package be.helha.crowdfunding.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@NamedQueries({ @NamedQuery(name = "findAllDonations", query = "SELECT d FROM Donation d"),
		@NamedQuery(name = "findDonationById", query = "SELECT d FROM Donation d WHERE d.id = :id"),
		@NamedQuery(name = "findDonationsByProject", query = "SELECT d FROM Donation d JOIN d.project p WHERE p.id=:projectId"),
		@NamedQuery(name = "findDonationsByUser", query = "SELECT d FROM Donation d JOIN d.user u WHERE u.email=:userEmail") })
public class Donation implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Date date;
	private double amount;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	private User user;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	private Project project;

	public Donation() {
	}

	public Donation(Date date, double amount, Project project) {

		this.date = date;
		this.amount = amount;
		this.project = project;
		this.user = null;
	}

	public Donation(Date date, double amount, User user) {

		this.date = date;
		this.amount = amount;
		this.project = null;
		this.user = user;
	}

	public Donation(Date date, double amount, User user, Project project) {

		this.date = date;
		this.amount = amount;
		this.project = project;
		this.user = user;
	}

	public Donation clone() {
		return (Donation) SerializationUtils.clone(this);
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public String getFrenchFormatDate() {
		return new SimpleDateFormat("dd/MM/yyyy ' ' hh:mm:ss aaa").format(date);
	}

	public User getUser() {
		return user;
	}

	public Project getProject() {
		return project;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((project == null) ? 0 : project.hashCode());
		result = prime * result + ((user == null) ? 0 : user.hashCode());
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
		Donation other = (Donation) obj;
		if (project == null) {
			if (other.project != null)
				return false;
		} else if (!project.equals(other.project))
			return false;
		if (user == null) {
			if (other.user != null)
				return false;
		} else if (!user.equals(other.user))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

}
