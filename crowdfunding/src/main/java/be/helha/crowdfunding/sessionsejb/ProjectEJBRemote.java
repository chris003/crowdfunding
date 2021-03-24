package be.helha.crowdfunding.sessionsejb;

import java.util.List;

import javax.ejb.Remote;

import be.helha.crowdfunding.entities.Project;
import be.helha.crowdfunding.entities.User;

@Remote
public interface ProjectEJBRemote {

	public List<Project> getProjects();

	public List<Project> getProjectsByUser(User user);

	public Project getProjectById(int id);

	public void removeProject(Project project);

	public Project updateProject(Project project);

	public void refreshProject(Project project);

	public void detachProject(Project project);

}
