package be.helha.crowdfunding.controllers;

import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import be.helha.crowdfunding.entities.Address;
import be.helha.crowdfunding.entities.User;
import be.helha.crowdfunding.sessionsejb.UserEJB;

@Named
@SessionScoped
public class ProfilView implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(ProfilView.class.getName());
	@Inject
	private UserEJB userEJB;
	private String email;
	private String lastName;
	private String firstName;
	private String telephone;
	private String street;
	private String streetNum;
	private String postalCode;
	private String locality;
	private String country;

	public ProfilView() {
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		User userSession = (User) sessionMap.get("User");

		email = userSession.getEmail();
		lastName = userSession.getLastName();
		firstName = userSession.getFirstName();
		telephone = userSession.getTelephone();
		street = userSession.getAddress().getStreet();
		streetNum = userSession.getAddress().getStreetNum();
		postalCode = userSession.getAddress().getPostalCode();
		locality = userSession.getAddress().getLocality();
		country = userSession.getAddress().getCountry();
	}

	public String getEmail() {
		return email;
	}

	public String getLastName() {
		return lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getTelephone() {
		return telephone;
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

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
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

	public void modifie() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		User userSession = (User) sessionMap.get("User");

		if (!Pattern.compile("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$").matcher(telephone).find()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Veuillez entrer un numéro de téléphone valide !", null));
		}

		else {

			User userToModifie = new User(userSession.getEmail(), null, lastName, firstName, telephone, street,
					streetNum, postalCode, locality, country);
			try {
				userEJB.updateProfil(userToModifie);
			} catch (Exception e) {
				context.addMessage(null,
						new FacesMessage(FacesMessage.SEVERITY_ERROR, "Votre profil n'a pas pu être modifié !", null));
			}

			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Votre profil a bien été modifié !", null));
		}
	}

}
