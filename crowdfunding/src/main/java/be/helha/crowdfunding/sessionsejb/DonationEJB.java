package be.helha.crowdfunding.sessionsejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import be.helha.crowdfunding.daos.DAODonation;
import be.helha.crowdfunding.entities.Donation;
import be.helha.crowdfunding.entities.Project;
import be.helha.crowdfunding.entities.User;

@Stateless
@LocalBean
public class DonationEJB implements DonationEJBRemote {

	@EJB
	private DAODonation dao;

	@Override
	public List<Donation> getDonationsByUser(User user) {
		return dao.getDonationsByUser(user);
	}

	@Override
	public List<Donation> getDonationsByProject(Project project) {
		return dao.getDonationsByProject(project);
	}

	@Override
	public void detachDonation(Donation donation) {
		dao.detachDonation(donation);
	}

	@Override
	public Donation findDonationById(int id) {
		return dao.findDonationById(id);
	}

	@Override
	public List<Donation> getDonations() {
		return dao.getDonations();
	}

	@Override
	public void removeDonation(Donation donation) {
		dao.removeDonation(donation);

	}

	@Override
	public Donation updateDonation(Donation donation) {
		return dao.updateDonation(donation);
	}

}
