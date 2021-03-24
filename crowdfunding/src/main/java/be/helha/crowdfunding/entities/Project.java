package be.helha.crowdfunding.entities;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

import be.helha.crowdfunding.exceptions.DuplicateElementException;

@Entity
@NamedQueries({ @NamedQuery(name = "findAllProjects", query = "SELECT p FROM Project p"),
		@NamedQuery(name = "findProjectById", query = "SELECT p FROM Project p WHERE p.id=:id"),
		@NamedQuery(name = "findProjectsByUser", query = "SELECT p FROM Project p JOIN p.user u WHERE u.email=:userEmail") })
public class Project implements Serializable, Cloneable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String entitled;
	private String slogan;
	@Column(length = 500)
	private String description;
	@Column(length = 500)
	private String projectLeader;
	private double amount;
	private String telephone;
	private String email;
	private Date endingDate;
	private String imageEntitled;
	@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
	private Address address;
	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH })
	private User user;
	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "project")
	private List<Donation> donations;

	public Project() {
	}

	public Project(String entitled, String slogan, String description, String projectLeader, double amount,
			Date endingDate, String telephone, String email, String imageEntitled, String street, String streetNum,
			String postalCode, String locality, String country) {

		this.entitled = entitled;
		this.slogan = slogan;
		this.description = description;
		this.projectLeader = projectLeader;
		this.amount = amount;
		this.endingDate = endingDate;
		this.telephone = telephone;
		this.email = email;
		this.imageEntitled = imageEntitled;
		this.address = new Address(street, streetNum, postalCode, locality, country);
		this.user = null;
		this.donations = new ArrayList<Donation>();
	}

	public Project clone() {
		return (Project) SerializationUtils.clone(this);
	}

	public Integer getId() {
		return id;
	}

	public String getEntitled() {
		return entitled;
	}

	public String getSlogan() {
		return slogan;
	}

	public String getDescription() {
		return description;
	}

	public String getProjectLeader() {
		return projectLeader;
	}

	public double getAmount() {
		return amount;
	}

	public Date getEndingDate() {
		return endingDate;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getEmail() {
		return email;
	}

	public String getImageEntitled() {
		return imageEntitled;
	}

	public Address getAddress() {
		return address;
	}

	public User getUser() {
		return user;
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

	public Donation getDonation(User user) {
		for (Donation donation : donations) {
			if (donation.getUser().equals(user)) {
				return donation;
			}
		}
		return null;
	}

	public long getRemainingDays() {
		Date dateToday = new Date();

		long diffInMillies = endingDate.getTime() - dateToday.getTime();
		long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);

		if (diff < 0) {
			return 0;
		} else if (diff == 0 && diffInMillies > 0) {
			return 1;
		}

		return diff;
	}

	public double getTotalContributions() {
		double totalContributions = 0.;
		for (Donation donation : donations) {
			totalContributions += donation.getAmount();
		}
		return totalContributions;
	}

	public int getPercentContributions() {
		return (int) ((getTotalContributions() / amount) * 100);
	}

	public int getTotalContributors() {
		return donations.size();
	}

	public String getEndingDateFormatted() {
		return new SimpleDateFormat("yyyy-MM-dd").format(endingDate);
	}

	public String getFrenchEndingDate() {
		return new SimpleDateFormat("EEEEEEEEEE dd MMMMMMMMMM yyyy", Locale.FRANCE).format(endingDate);
	}

	public String getSmallFrenchEndingDate() {
		return new SimpleDateFormat("dd/MM/yyyy").format(endingDate);
	}
	
	public double getUserContribution(User user) {
		for (Donation donation : donations) {
			if (donation.getUser().equals(user)) {
				return donation.getAmount();
			}
		}
		return 0.;
	}
	
	public boolean isContributionsCompletes()
	{
		if(this.getTotalContributions() < this.getAmount())
		{
			return false;
		}
		return true;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setEntitled(String entitled) {
		this.entitled = entitled;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setEndingDate(Date endingDate) {
		this.endingDate = endingDate;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setImageEntitled(String imageEntitled) {
		this.imageEntitled = imageEntitled;
	}

	public void setAddress(String street, String streetNum, String postalCode, String locality, String country) {
		this.address = new Address(street, streetNum, postalCode, locality, country);
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public boolean existContributor(User user) {
		for (Donation donation : donations) {
			if (donation.getUser().equals(user)) {
				return true;
			}
		}
		return false;
	}
	
	public boolean addDonation(Date date, double amount, User user) {
		Donation donation = new Donation(date, amount, user);
		donation.setProject(this);
		if (donations.contains(donation)) {
			throw new DuplicateElementException("Le don d'un montant de  \" " + donation.getAmount()
					+ " \" a déjà été ajouté au projet \"" + donation.getProject().getEntitled() + "\" !");
		}
		return donations.add(donation);
	}

	
	

	public boolean removeDonation(Donation donation) {
		return donations.remove(donation);
	}

	public Donation removeDonation(int index) {
		return donations.remove(index);
	}

	public Donation removeDonation(User user) {
		for (int i = 0; i < donations.size(); i++) {
			if (donations.get(i).getUser().equals(user)) {
				return donations.remove(i);
			}
		}
		return null;
	}

	public Donation replaceDonation(Date date, double amount, User user) {
		Donation donation = new Donation(date, amount, user);
		donation.setProject(this);
		for (int i = 0; i < donations.size(); i++) {
			if (donations.get(i).getUser().equals(user)) {
				return donations.set(i, donation);
			}
		}
		return null;
	}

	public void setDonations(List<Donation> donations) {
		this.donations.clear();
		this.donations.addAll(donations);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((entitled == null) ? 0 : entitled.hashCode());
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
		Project other = (Project) obj;
		if (entitled == null) {
			if (other.entitled != null)
				return false;
		} else if (!entitled.equals(other.entitled))
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
