package be.helha.crowdfunding.sessionsejb;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;

import be.helha.crowdfunding.daos.DAOUser;
import be.helha.crowdfunding.entities.Project;
import be.helha.crowdfunding.entities.User;

@Stateless
@LocalBean
public class UserEJB implements UserEJBRemote {

	@EJB
	private DAOUser dao;

	@Override
	public User createUser(User user) {
		return dao.createUser(user);
	}

	@Override
	public User findUserById(String id) {
		return dao.findUserById(id);
	}

	@Override
	public User updateProfil(User userToUpdate) {
		return dao.updateProfil(userToUpdate);
	}

	@Override
	public User updatePassword(User userToUpdate) {
		return dao.updatePassword(userToUpdate);
	}

	@Override
	public User update(User user) {
		return dao.update(user);
	}

	@Override
	public User addProject(User user, Project project) {
		return dao.addProject(user, project);
	}

	@Override
	public List<User> getUsers() {
		return dao.getUsers();
	}

	@Override
	public void removeUser(User user) {
		dao.removeUser(user);
	}

	@Override
	public User updateUser(User user) {
		return dao.updateUser(user);
	}

	@Override
	public void refreshUser(User user) {
		dao.refreshUser(user);
	}

	@Override
	public void detachUser(User user) {
		dao.detachUser(user);
	}

}
