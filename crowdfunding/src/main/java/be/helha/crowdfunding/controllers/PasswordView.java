package be.helha.crowdfunding.controllers;

import java.io.Serializable;
import java.util.Map;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import be.helha.crowdfunding.entities.User;
import be.helha.crowdfunding.sessionsejb.UserEJB;

@Named
@SessionScoped
public class PasswordView implements Serializable {
	private static final long serialVersionUID = 1L;
	private static Logger log = Logger.getLogger(PasswordView.class.getName());
	@Inject
	private UserEJB userEJB;
	private String password;
	private String confirmPassword;

	public String getPassword() {
		return password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public void modifie() {
		FacesContext context = FacesContext.getCurrentInstance();
		ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
		Map<String, Object> sessionMap = externalContext.getSessionMap();
		User userSession = (User) sessionMap.get("User");

		if (!password.equals(confirmPassword)) {
			context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Les deux mots de passe ne correspondent pas !", null));
		}

		else {

			User userToModifie = new User(userSession.getEmail(), password, null, null, null, null, null, null, null,
					null);
			try {
				userEJB.updatePassword(userToModifie);
			} catch (Exception e) {
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						"Votre mot de passe n'a pas pu être modifié !", null));
			}

			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_FATAL, "Votre mot de passe a bien été modifié !", null));
		}
	}

}
