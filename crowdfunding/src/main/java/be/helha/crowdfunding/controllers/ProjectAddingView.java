package be.helha.crowdfunding.controllers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.Part;

import org.apache.commons.io.FilenameUtils;

import be.helha.crowdfunding.entities.Project;
import be.helha.crowdfunding.entities.User;
import be.helha.crowdfunding.sessionsejb.ProjectEJB;
import be.helha.crowdfunding.sessionsejb.UserEJB;

@Named
@SessionScoped
public class ProjectAddingView implements Serializable {
	private static final long serialVersionUID = 3254181235309041386L;
	private static Logger log = Logger.getLogger(ProjectAddingView.class.getName());

	@Inject
	private UserEJB userEJB;
	@Inject
	private ProjectEJB projectEJB;
	private String entitled;
	private String description;
	private String slogan;
	private String projectLeader;
	private double amount;
	private String telephone;
	private String email;
	private String endingDate;
	private String street;
	private String streetNum;
	private String postalCode;
	private String locality;
	private String country;
	private String imageEntitled;
	// A changer
	private String folder = "/Users/cdecr/git/repository/crowdfunding/src/main/webapp/images";
	private Part uploadedFile;

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

	public String getTelephone() {
		return telephone;
	}

	public String getEmail() {
		return email;
	}

	public String getImageEntitled() {
		return imageEntitled;
	}

	public String getEndingDate() {
		return endingDate;
	}

	public String getStreet() {
		return street;
	}

	public String getStreetNum() {
		return streetNum;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public String getLocality() {
		return locality;
	}

	public String getCountry() {
		return country;
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

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setImageEntitled(String imageEntitled) {
		this.imageEntitled = imageEntitled;
	}

	public void setEndingDate(String endingDate) {
		this.endingDate = endingDate;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setStreetNum(String streetNum) {
		this.streetNum = streetNum;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public Part getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(Part uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String upload() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		User userSession = (User) sessionMap.get("User");
		Date endingDateFormatted = null;
		try {
			endingDateFormatted = new SimpleDateFormat("yyyy-MM-dd").parse(endingDate);
		} catch (ParseException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Un problème au niveau de la date de fin est survenu !", null));
			return "";
		}

		if (endingDateFormatted.before(new Date())) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"La date de fin doit être postérieure à la date du jour !", null));
			return "";
		}

		if (amount <= 0) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Le montant doit être positif !", null));
			return "";
		}

		amount = (double) Math.round(amount * 100) / 100;

		Project project = new Project(entitled, slogan, description, projectLeader, amount, endingDateFormatted,
				telephone, email, null, street, streetNum, postalCode, locality, country);

		try {
			userEJB.addProject(userSession, project);
		} catch (Exception e) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Votre projet \"" + entitled + "\" n'a pas pu être ajouté !", null));
			return "";
		}

		User user = userEJB.findUserById(userSession.getEmail());
		project = user.getProject(user.getProjects().size() - 1);

		try (InputStream input = uploadedFile.getInputStream()) {
			String extension = FilenameUtils.getExtension(uploadedFile.getSubmittedFileName());

			if (!extension.equals("jpeg") && !extension.equals("jpg") && !extension.equals("gif") && !extension.equals("png")) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Votre projet a bien été ajouté mais veuillez ajouter une image au format jpeg, jpg, gif ou png !", null));
				return "";
			}

			int index = project.getId();
			String fileName = "project" + index + "." + extension;
			Files.copy(input, new File(folder, fileName).toPath());
			project = projectEJB.getProjectById(project.getId());
			project.setImageEntitled(fileName);
			projectEJB.updateProject(project);
		} catch (IOException e) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "Un problème avec l'image est survenu !", null));
			return "";
		}

		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL,
				"Votre projet \"" + entitled + "\" a bien été ajouté !", null));
		return "";
	}

}
