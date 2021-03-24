package be.helha.crowdfunding.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;

import be.helha.crowdfunding.entities.Donation;
import be.helha.crowdfunding.entities.Project;
import be.helha.crowdfunding.entities.User;
import be.helha.crowdfunding.sessionsejb.DonationEJB;
import be.helha.crowdfunding.sessionsejb.ProjectEJB;
import be.helha.crowdfunding.sessionsejb.UserEJB;

@Named
@SessionScoped
public class UsersAdminView implements Serializable {

	private static final long serialVersionUID = 3254181235309041386L;
	private static Logger log = Logger.getLogger(UsersAdminView.class.getName());
	@Inject
	private UserEJB userEJB;
	@Inject
	private DonationEJB donationEJB;
	@Inject
	private ProjectEJB projectEJB;

	private User user;

	private Donation donation;

	private Project project;

	private double amount;

	private int counter = 0;
	private boolean control = false;

	private String lastName;
	private String firstName;
	private String email;
	private String password;
	private String confirmPassword;
	private String telephone;
	private String street;
	private String streetNum;
	private String postalCode;
	private String locality;
	private String country;

	private String entitled;
	private String description;
	private String slogan;
	private String projectLeader;
	private String endingDate;

	private String imageEntitled;
	// A changer
	private String folder = "/Users/cdecr/git/repository/crowdfunding/src/main/webapp/images";

	private Part uploadedFile;

	public UsersAdminView() {

	}

	public User getUser() {
		userEJB.detachUser(user);
		setUser(userEJB.findUserById(user.getEmail()));

		return user;
	}

	public void setUser(User user) {
		this.user = user;
		lastName = user.getLastName();
		firstName = user.getFirstName();
		email = user.getEmail();
		telephone = user.getTelephone();
		street = user.getAddress().getStreet();
		streetNum = user.getAddress().getStreetNum();
		postalCode = user.getAddress().getPostalCode();
		locality = user.getAddress().getLocality();
		country = user.getAddress().getCountry();
	}

	public List<User> getUsers() {
		counter = 0;
		control = false;
		return userEJB.getUsers();
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getStreetNum() {
		return streetNum;
	}

	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getEntitled() {
		return entitled;
	}

	public void setEntitled(String entitled) {
		this.entitled = entitled;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getSlogan() {
		return slogan;
	}

	public void setSlogan(String slogan) {
		this.slogan = slogan;
	}

	public String getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(String projectLeader) {
		this.projectLeader = projectLeader;
	}

	public String getEndingDate() {
		return endingDate;
	}

	public void setEndingDate(String endingDate) {
		this.endingDate = endingDate;
	}

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getImageEntitled() {
		return imageEntitled;
	}

	public void setImageEntitled(String imageEntitled) {
		this.imageEntitled = imageEntitled;
	}

	public List<Donation> getDonationsByProject(Project project) {
		return donationEJB.getDonationsByProject(project);
	}

	public List<Donation> getDonationsByUser(User user) {
		return donationEJB.getDonationsByUser(user);

	}

	public Donation getDonation() {
		donationEJB.detachDonation(donation);
		setDonation(donationEJB.findDonationById(donation.getId()));

		return donation;
	}

	public void setDonation(Donation donation) {
		this.donation = donation;
		amount = donation.getAmount();
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public List<Project> getProjectsByUser(User user) {
		return projectEJB.getProjectsByUser(user);
	}

	public Project getProject() {
		projectEJB.detachProject(project);
		setProject(projectEJB.getProjectById(project.getId()));

		return project;
	}

	public void setProject(Project project) {
		this.project = project;
		entitled = project.getEntitled();
		description = project.getDescription();
		slogan = project.getSlogan();
		projectLeader = project.getProjectLeader();
		amount = project.getAmount();
		telephone = project.getTelephone();
		email = project.getEmail();
		endingDate = new SimpleDateFormat("yyyy-MM-dd").format(project.getEndingDate());
		street = project.getAddress().getStreet();
		streetNum = project.getAddress().getStreetNum();
		postalCode = project.getAddress().getPostalCode();
		locality = project.getAddress().getLocality();
		country = project.getAddress().getCountry();
		imageEntitled = project.getImageEntitled();
	}

	public int getCounter() {
		if (control == false) {
			control = true;
			return ++counter;
		}
		control = false;
		return counter;
	}

	public String displayDonationsList(User user) {
		setUser(userEJB.findUserById(user.getEmail()));
		counter = 0;
		control = false;
		return "userDonationsList.xhtml?faces-redirect=true";
	}

	public String displayDonationDescription(Donation donation) {
		setDonation(donationEJB.findDonationById(donation.getId()));
		return "userDonationDescription.xhtml?faces-redirect=true";
	}

	public String displayUserDescription(User user) {
		setUser(user);
		return "userDescription.xhtml?faces-redirect=true";
	}

	public String displayDonationsList(Project project) {
		setProject(projectEJB.getProjectById(project.getId()));
		counter = 0;
		control = false;
		return "userProjectDonationsList.xhtml?faces-redirect=true";
	}

	public String displayProjectDonationDescription(Donation donation) {
		setDonation(donationEJB.findDonationById(donation.getId()));
		return "userProjectDonationDescription.xhtml?faces-redirect=true";
	}

	public String displayProjectsList(User user) {
		setUser(userEJB.findUserById(user.getEmail()));
		counter = 0;
		control = false;
		return "userProjectsList.xhtml?faces-redirect=true";
	}

	public String displayProjectDescription(Project project) {
		setProject(projectEJB.getProjectById(project.getId()));
		return "userProjectDescription.xhtml?faces-redirect=true";
	}

	public void modifie() {
		FacesContext context = FacesContext.getCurrentInstance();

		if (!password.equals(confirmPassword)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Les deux mots de passe ne correspondent pas !", null));
		}

		else if (!Pattern.compile("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$").matcher(telephone).find()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Veuillez entrer un numéro de téléphone valide !", null));
		}

		else {

			try {
				User userToModifie = new User(user.getEmail(), null, lastName, firstName, telephone, street, streetNum,
						postalCode, locality, country);
				userEJB.updateProfil(userToModifie);
			} catch (Exception e) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Votre profil n'a pas pu être modifié !", null));
			}

			if (password != null) {
				User userToModifie = new User(user.getEmail(), password, null, null, null, null, null, null, null,
						null);
				try {
					userEJB.updatePassword(userToModifie);
				} catch (Exception e) {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Votre mot de passe n'a pas pu être modifié !", null));
				}
			}

			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Votre profil a bien été modifié !", null));

		}
	}

	public String removeUser(User user) {
		userEJB.removeUser(user);
		return "usersList.xhtml?faces-redirect=true";
	}

	public String removeDonation(Donation donation) {
		donationEJB.removeDonation(donation);
		return "userDonationsList.xhtml?faces-redirect=true";
	}

	public void modifieDonation() {
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			donation.setAmount(amount);
			donationEJB.updateDonation(donation);
		} catch (Exception e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le don n'a pas pu être modifié !", null));
		}

		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Le don a bien été modifié !", null));

	}

	public String removeProject(Project project) {
		projectEJB.removeProject(project);
		return "userProjectsList.xhtml?faces-redirect=true";
	}

	public void modifieProject() {
		FacesContext context = FacesContext.getCurrentInstance();

		Date endingDateFormatted = null;
		try {
			endingDateFormatted = new SimpleDateFormat("yyyy-MM-dd").parse(endingDate);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Un problème au niveau de la date de fin est survenu !", null));
		}

		if (endingDateFormatted.before(new Date())) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La date de fin doit être postérieure à la date du jour !", null));
		}

		else if (amount <= 0) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le montant doit être positif !", null));
		} else {
			amount = (double) Math.round(amount * 100) / 100;

			try (InputStream input = uploadedFile.getInputStream()) {
				String extension = FilenameUtils.getExtension(uploadedFile.getSubmittedFileName());

				if (extension != "jpeg" && extension != "jpg" && extension != "gif" && extension != "png") {
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							"Veuillez ajouter une image au format jpeg, jpg, gif ou png !", null));
				}

				int index = project.getId();
				File file = new File(folder + "/" + project.getImageEntitled());
				file.delete();
				String fileName = "project" + index + "." + extension;
				Files.copy(input, new File(folder, fileName).toPath());
				project = projectEJB.getProjectById(project.getId());
				project.setImageEntitled(fileName);
				projectEJB.updateProject(project);

			} catch (IOException e) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un problème avec l'image est survenu !", null));
			}

			try {
				project.setEntitled(entitled);
				project.setDescription(description);
				project.setSlogan(slogan);
				project.setProjectLeader(projectLeader);
				project.setAmount(amount);
				project.setTelephone(telephone);
				project.setEmail(email);
				project.setEndingDate(endingDateFormatted);
				project.setAddress(street, streetNum, postalCode, locality, country);
				projectEJB.updateProject(project);
			} catch (Exception e) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le projet n'a pas pu être modifié !", null));
			}

			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Le projet a bien été modifié !", null));
		}
	}

}
