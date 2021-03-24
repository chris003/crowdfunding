package be.helha.crowdfunding.sessionsejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import be.helha.crowdfunding.daos.DAOProject;
import be.helha.crowdfunding.entities.Project;
import be.helha.crowdfunding.entities.User;

@Stateless
@LocalBean
public class ProjectEJB implements ProjectEJBRemote {

	@EJB
	private DAOProject dao;

	@Override
	public List<Project> getProjects() {
		return dao.getProjects();
	}

	@Override
	public List<Project> getProjectsByUser(User user) {
		return dao.getProjectsByUser(user);
	}

	@Override
	public Project getProjectById(int id) {
		return dao.getProjectById(id);
	}

	@Override
	public void removeProject(Project project) {
		dao.removeProject(project);

	}

	@Override
	public Project updateProject(Project project) {
		return dao.updateProjet(project);
	}

	@Override
	public void refreshProject(Project project) {
		dao.refreshProject(project);

	}

	@Override
	public void detachProject(Project project) {
		dao.detachProject(project);

	}

}
