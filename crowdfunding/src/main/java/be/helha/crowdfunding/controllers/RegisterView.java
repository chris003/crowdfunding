package be.helha.crowdfunding.controllers;

import java.io.Serializable;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import be.helha.crowdfunding.entities.User;
import be.helha.crowdfunding.sessionsejb.UserEJB;

@Named
@SessionScoped
public class RegisterView implements Serializable {
	private static final long serialVersionUID = 1685823449195612778L;
	private static Logger log = Logger.getLogger(RegisterView.class.getName());
	@Inject
	private UserEJB userEJB;
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

	public String getConfirmPassword() {
		return confirmPassword;
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

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
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

	public String register() {
		FacesContext context = FacesContext.getCurrentInstance();
		log.info(password + " " + confirmPassword);
		if (!password.equals(confirmPassword)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Les deux mots de passe ne correspondent pas !", null));
			return "";
		} else if (userEJB.findUserById(email) != null) {
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, "L'adresse e-mail existe déjà !", null));
			return "";
		} else if (!Pattern.compile("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$").matcher(telephone).find()) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Veuillez entrer un numéro de téléphone valide !", null));
			return "";
		} else {
			User user = new User(email, password, lastName, firstName, telephone, street, streetNum, postalCode,
					locality, country);
			userEJB.createUser(user);
			log.info("New user created with e-mail: " + email + " and name: " + firstName + " " + lastName);
			return "regdone";
		}
	}

}