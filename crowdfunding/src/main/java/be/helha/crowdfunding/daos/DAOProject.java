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
public class DAOProject {

	@PersistenceContext(unitName = "crowdfunding_JTA")
	private EntityManager em;

	public DAOProject() {

	}

	public List<Project> getProjects() {
		TypedQuery<Project> query = em.createNamedQuery("findAllProjects", Project.class);

		List<Project> projects = query.getResultList();

		for (Project project : projects) {
			TypedQuery<Donation> queryDonations = em.createNamedQuery("findDonationsByProject", Donation.class);
			queryDonations.setParameter("projectId", project.getId());

			List<Donation> donations = queryDonations.getResultList();
			project.setDonations(donations);
		}

		return projects;
	}

	public List<Project> getProjectsByUser(User user) {
		TypedQuery<Project> query = em.createNamedQuery("findProjectsByUser", Project.class);
		query.setParameter("userEmail", user.getEmail());

		List<Project> projects = query.getResultList();

		for (Project project : projects) {
			TypedQuery<Donation> queryDonations = em.createNamedQuery("findDonationsByProject", Donation.class);
			queryDonations.setParameter("projectId", project.getId());

			List<Donation> donations = queryDonations.getResultList();
			project.setDonations(donations);
		}

		return projects;
	}

	public Project getProjectById(int id) {
		TypedQuery<Project> query = em.createNamedQuery("findProjectById", Project.class);
		query.setParameter("id", id);

		try {
			Project project = query.getSingleResult();
			return project;
		} catch (Exception e) {
			return null;
		}
	}

	public void removeProject(Project project) {
		if (!em.contains(project)) {
			project = em.merge(project);
		}

		em.remove(project);
	}

	public Project updateProjet(Project project) {
		return em.merge(project);
	}

	public void refreshProject(Project project) {
		em.refresh(project);
	}

	public void detachProject(Project project) {
		if (em.contains(project)) {
			em.detach(project);
		}
	}

}
