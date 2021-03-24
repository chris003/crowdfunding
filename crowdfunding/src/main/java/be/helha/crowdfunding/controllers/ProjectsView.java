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

import be.helha.crowdfunding.entities.Project;
import be.helha.crowdfunding.entities.User;
import be.helha.crowdfunding.sessionsejb.ProjectEJB;
import be.helha.crowdfunding.sessionsejb.UserEJB;

@Named
@SessionScoped
public class ProjectsView implements Serializable {

	private static final long serialVersionUID = 3254181235309041386L;
	private static Logger log = Logger.getLogger(ProjectsView.class.getName());
	@Inject
	private ProjectEJB projectEJB;
	@Inject
	private UserEJB userEJB;
	
	private Project project;
	
	private double donationAmount;

	public ProjectsView() {

	}
	
	public List<Project> getProjects()
	{
		return projectEJB.getProjects();
	}
	
	public List<Project> getProjectsByUser(User user)
	{
		return projectEJB.getProjectsByUser(user);
	}

	public Project getProject() {
		projectEJB.detachProject(project);
		return projectEJB.getProjectById(project.getId());
	}
	
	public void setProject(Project project) {
		this.project = project;
	}

	public double getDonationAmount() {
		return donationAmount;
	}

	public void setDonationAmount(double donationAmount) {
		this.donationAmount = donationAmount;
	}

	public String displayProjectDescription(Project project)
	{
		setProject(project);
		return "projectDescription.xhtml?faces-redirect=true";
	}
	
	public String displayDonationsList(Project project)
	{
		setProject(project);
		return "donationsByProjectList.xhtml?faces-redirect=true";
	}
	
	public String removeProject(Project project)
	{
		projectEJB.removeProject(project);
		return "projectsList.xhtml?faces-redirect=true";
	}
	
	public void addDonation()
	{
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		User userSession = (User) sessionMap.get("User");
		User userFromDB = userEJB.findUserById(userSession.getEmail());
		project.addDonation(new Date(), donationAmount, userFromDB);
		projectEJB.updateProject(project);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Votre don a bien été modifié !", null));
	}
	
	public void modifieDonation()
	{
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		User userSession = (User) sessionMap.get("User");
		User userFromDB = userEJB.findUserById(userSession.getEmail());
		project.replaceDonation(new Date(), donationAmount, userFromDB);
		projectEJB.updateProject(project);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Votre don a bien été modifié !", null));
	}
	
	public void removeDonation()
	{
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		User userSession = (User) sessionMap.get("User");
		User userFromDB = userEJB.findUserById(userSession.getEmail());
		project.removeDonation(userFromDB);
		projectEJB.updateProject(project);
		FacesContext context = FacesContext.getCurrentInstance();
		context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_FATAL, "Votre don a bien été supprimée !", null));
	}

}
