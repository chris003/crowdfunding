package be.helha.crowdfunding.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import be.helha.crowdfunding.exceptions.DuplicateElementException;

@Entity
@NamedQueries({ @NamedQuery(name = "findAllUsers", query = "SELECT u FROM User u"),
		@NamedQuery(name = "findUserById", query = "SELECT u FROM User u WHERE u.email = :email") })
public class User implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	private String email;

	private String lastName;
	private String firstName;
	private String password;
	private String telephone;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Address address;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	private List<Project> projects;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "user")
	private List<Donation> donations;

	public User() {
	}

	public User(String email, String password, String lastName, String firstName, String telephone, String street,
			String streetNum, String postalCode, String locality, String country) {

		this.lastName = lastName;
		this.firstName = firstName;
		this.email = email;
		this.password = password;
		this.telephone = telephone;
		this.address = new Address(street, streetNum, postalCode, locality, country);
		this.projects = new ArrayList<Project>();
		this.donations = new ArrayList<Donation>();
	}

	public User clone() {
		return (User) SerializationUtils.clone(this);
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

	public String getTelephone() {
		return telephone;
	}

	public Address getAddress() {
		return address;
	}

	public List<Project> getProjects() {

		List<Project> list = new ArrayList<Project>();

		for (Project p : projects) {
			list.add(p.clone());
		}
		return list;
	}

	public Project getProject(int index) {
		return projects.get(index);
	}

	public Donation getDonation(Project project) {
		for (Donation donation : donations) {
			if (donation.getProject().equals(project)) {
				return donation;
			}
		}
		return null;
	}

	public List<Donation> getDonations() {

		List<Donation> list = new ArrayList<Donation>();

		for (Donation d : donations) {
			list.add(d.clone());
		}
		return list;
	}

	public Donation getDonation(int index) {
		return donations.get(index);
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setAddress(String street, String streetNum, String postalCode, String locality, String country) {
		this.address = new Address(street, streetNum, postalCode, locality, country);
	}
	
	public void setProjects(List<Project> projects) {
		this.projects.clear();
		this.projects.addAll(projects);
	}
	
	public void setDonations(List<Donation> donations) {
		this.donations.clear();
		this.donations.addAll(donations);
	}

	public boolean addProject(String entitled, String slogan, String description, String projectLeader, double amount,
			Date endingDate, String telephone, String email, String imageEntitled, String street, String streetNum,
			String postalCode, String locality, String country) {
		Project project = new Project(entitled, slogan, description, projectLeader, amount, endingDate, telephone,
				email, imageEntitled, street, streetNum, postalCode, locality, country);
		project.setUser(this);
		if (projects.contains(project)) {
			throw new DuplicateElementException("Le projet \"" + project.getEntitled() + "\" a déjà été ajouté !");
		}
		return projects.add(project);
	}

	public boolean removeProject(Project project) {
		return projects.remove(project);
	}

	public Project removeProject(int index) {
		return projects.remove(index);
	}

	public Project replaceProject(int index, String entitled, String slogan, String description, String projectLeader,
			double amount, Date endingDate, String telephone, String email, String imageEntitled, String street,
			String streetNum, String postalCode, String locality, String country) {
		Project project = new Project(entitled, slogan, description, projectLeader, amount, endingDate, telephone,
				email, imageEntitled, street, streetNum, postalCode, locality, country);
		project.setDonations(projects.get(index).getDonations());
		project.setUser(projects.get(index).getUser());
		if (projects.contains(project)) {
			throw new DuplicateElementException("Le projet \" " + project.getEntitled() + " \" a déjà été ajouté !");
		}
		return projects.set(index, project);
	}

	

	public boolean addDonation(Date date, double amount, Project project) {
		Donation donation = new Donation(date, amount, project);
		donation.setUser(this);
		if (donations.contains(donation)) {
			throw new DuplicateElementException("Le don d'un montant de  \" " + donation.getAmount()
					+ " \" a déjà été ajouté au projet \"" + donation.getProject().getEntitled() + "\" !");
		}
		return donations.add(donation);
	}

	public boolean removeDonation(Donation donation) {
		return donations.remove(donation);
	}

	public Donation removeDonation(int index) {
		return donations.remove(index);
	}

	public Donation removeDonation(Project project) {
		for (int i = 0; i < donations.size(); i++) {
			if (donations.get(i).getProject().equals(project)) {
				return donations.remove(i);
			}
		}
		return null;
	}

	public Donation replaceDonation(Date date, double amount, Project project) {
		Donation donation = new Donation(date, amount, project);
		donation.setUser(this);

		for (int i = 0; i < donations.size(); i++) {
			if (donations.get(i).getProject().equals(project)) {
				return donations.set(i, donation);
			}
		}
		return null;
	}

	

	public boolean containsDonation(Donation donation) {
		return donations.contains(donation);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
