package be.helha.crowdfunding.sessionsejb;

import java.util.List;

import javax.ejb.Remote;

import be.helha.crowdfunding.entities.Project;
import be.helha.crowdfunding.entities.User;

@Remote
public interface UserEJBRemote {

	public User createUser(User user);

	public User findUserById(String id);

	public User updateProfil(User userToUpdate);

	public User updatePassword(User userToUpdate);

	public User update(User user);

	public User addProject(User user, Project project);

	public List<User> getUsers();

	public void removeUser(User user);

	public User updateUser(User user);

	public void refreshUser(User user);

	public void detachUser(User user);

}
