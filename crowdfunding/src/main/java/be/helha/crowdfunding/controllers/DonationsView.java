package be.helha.crowdfunding.controllers;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import be.helha.crowdfunding.entities.Donation;
import be.helha.crowdfunding.entities.Project;
import be.helha.crowdfunding.entities.User;
import be.helha.crowdfunding.sessionsejb.DonationEJB;
import be.helha.crowdfunding.sessionsejb.ProjectEJB;
import be.helha.crowdfunding.sessionsejb.UserEJB;

@Named
@SessionScoped
public class DonationsView implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(DonationsView.class.getName());
	@Inject
	private DonationEJB donationEJB;
	@Inject
	private ProjectEJB projectEJB;
	@Inject
	private UserEJB userEJB;
	User userSession;

	private Project project;

	private double donationAmount;

	public DonationsView() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		userSession = (User) sessionMap.get("User");
	}

	public List<Donation> getDonationsByUser() {
		return donationEJB.getDonationsByUser(userSession);

	}

	public List<Project> getProjects() {
		return projectEJB.getProjects();
	}

	public List<Project> getProjectsByUser(User user) {
		return projectEJB.getProjectsByUser(user);
	}

	public Project getProject() {
		projectEJB.detachProject(project);
		return projectEJB.getProjectById(project.getId());
	}

	public double getDonationAmount() {
		return donationAmount;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public void setDonationAmount(double donationAmount) {
		this.donationAmount = donationAmount;
	}

	public String displayProjectDescription(Project project) {
		setProject(project);
		return "projectDescriptionByDonation.xhtml?faces-redirect=true";
	}

	public String displayDonationsList(Project project) {
		setProject(project);
		return "donationsByProjectList.xhtml?faces-redirect=true";
	}

	public String removeProject(Project project) {
		projectEJB.removeProject(project);
		return "projectsList.xhtml?faces-redirect=true";
	}

	public void addDonation() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		User userSession = (User) sessionMap.get("User");
		User userFromDB = userEJB.findUserById(userSession.getEmail());
		project.addDonation(new Date(), donationAmount, userFromDB);
		projectEJB.updateProject(project);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Votre don a bien été ajouté !", null));
	}

	public void modifieDonation() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		User userSession = (User) sessionMap.get("User");
		User userFromDB = userEJB.findUserById(userSession.getEmail());
		project.replaceDonation(new Date(), donationAmount, userFromDB);
		projectEJB.updateProject(project);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Votre don a bien été modifié !", null));
	}

	public void removeDonation() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		User userSession = (User) sessionMap.get("User");
		User userFromDB = userEJB.findUserById(userSession.getEmail());
		project.removeDonation(userFromDB);
		projectEJB.updateProject(project);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null,
				new FacesMessage(FacesMessage.SEVERITY_FATAL, "Votre don a bien été supprimée !", null));
	}

}
