package be.helha.crowdfunding.daos;

import java.util.List;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import be.helha.crowdfunding.entities.Donation;
import be.helha.crowdfunding.entities.Project;
import be.helha.crowdfunding.entities.User;

@Stateless
@LocalBean
public class DAODonation {

	@PersistenceContext(unitName = "crowdfunding_JTA")
	private EntityManager em;

	public DAODonation() {

	}

	public List<Donation> getDonationsByUser(User user) {
		TypedQuery<Donation> query = em.createNamedQuery("findDonationsByUser", Donation.class);
		query.setParameter("userEmail", user.getEmail());

		List<Donation> donations = query.getResultList();

		return donations;
	}

	public List<Donation> getDonationsByProject(Project project) {
		TypedQuery<Donation> query = em.createNamedQuery("findDonationsByProject", Donation.class);
		query.setParameter("projectId", project.getId());

		List<Donation> donations = query.getResultList();

		return donations;
	}

	public void detachDonation(Donation donation) {
		if (em.contains(donation)) {
			em.detach(donation);
		}
	}

	public Donation findDonationById(int id) {
		TypedQuery<Donation> query = em.createNamedQuery("findDonationById", Donation.class);
		query.setParameter("id", id);

		try {
			Donation donation = query.getSingleResult();
			return donation;
		} catch (Exception e) {
			return null;
		}
	}

	public List<Donation> getDonations() {
		TypedQuery<Donation> query = em.createNamedQuery("findAllDonations", Donation.class);

		List<Donation> donations = query.getResultList();

		return donations;
	}

	public void removeDonation(Donation donation) {
		if (!em.contains(donation)) {
			donation = em.merge(donation);
		}

		em.remove(donation);
	}

	public Donation updateDonation(Donation donation) {
		return em.merge(donation);
	}

}
