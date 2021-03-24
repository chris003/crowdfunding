package be.helha.crowdfunding.daos;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import be.helha.crowdfunding.entities.Address;
import be.helha.crowdfunding.entities.Group;
import be.helha.crowdfunding.entities.Project;
import be.helha.crowdfunding.entities.User;
import be.helha.crowdfunding.util.AuthenticationUtils;

@Stateless
@LocalBean
public class DAOUser {

	@PersistenceContext(unitName = "crowdfunding_JTA")
	private EntityManager em;

	public DAOUser() {

	}

	public User createUser(User user) {
		try {
			user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
		} catch (Exception e) {
			Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
			e.printStackTrace();
		}
		Group group = new Group();
		group.setEmail(user.getEmail());
		group.setGroupname(Group.USERS_GROUP);
		em.persist(user);
		em.persist(group);

		return user;

	}

	public User findUserById(String id) {
		TypedQuery<User> query = em.createNamedQuery("findUserById", User.class);
		query.setParameter("email", id);

		try {
			User user = query.getSingleResult();
			return user;
		} catch (Exception e) {
			return null;
		}
	}

	public User updateProfil(User userToUpdate) {
		User user = findUserById(userToUpdate.getEmail());

		user.setLastName(userToUpdate.getLastName());
		user.setFirstName(userToUpdate.getFirstName());
		user.setTelephone(userToUpdate.getTelephone());

		user.setAddress(userToUpdate.getAddress().getStreet(), userToUpdate.getAddress().getStreetNum(),
				userToUpdate.getAddress().getPostalCode(), userToUpdate.getAddress().getLocality(),
				userToUpdate.getAddress().getCountry());

		em.merge(user);
		return user;
	}

	public User updatePassword(User userToUpdate) {
		User user = findUserById(userToUpdate.getEmail());

		try {
			user.setPassword(AuthenticationUtils.encodeSHA256(userToUpdate.getPassword()));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return em.merge(user);
	}

	public User update(User user) {
		return em.merge(user);
	}

	public User addProject(User user, Project project) {
		User userFromDB = findUserById(user.getEmail());

		userFromDB.addProject(project.getEntitled(), project.getSlogan(), project.getDescription(),
				project.getProjectLeader(), project.getAmount(), project.getEndingDate(), project.getTelephone(),
				project.getEmail(), project.getImageEntitled(), project.getAddress().getStreet(),
				project.getAddress().getStreetNum(), project.getAddress().getPostalCode(),
				project.getAddress().getLocality(), project.getAddress().getLocality());
		em.merge(userFromDB);
		return user;
	}

	public List<User> getUsers() {
		TypedQuery<User> query = em.createNamedQuery("findAllUsers", User.class);

		List<User> users = query.getResultList();

		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getEmail().equals("admin@decrooscrowdfunding.be")) {
				users.remove(i);
			}
		}

		return users;
	}

	public void removeUser(User user) {
		if (!em.contains(user)) {
			user = em.merge(user);
		}

		em.remove(user);
	}

	public User updateUser(User user) {
		return em.merge(user);
	}

	public void refreshUser(User user) {
		em.refresh(user);
	}

	public void detachUser(User user) {
		if (em.contains(user)) {
			em.detach(user);
		}
	}

}
