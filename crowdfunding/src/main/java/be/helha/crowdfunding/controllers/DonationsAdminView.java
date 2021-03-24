package be.helha.crowdfunding.controllers;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import be.helha.crowdfunding.entities.Donation;
import be.helha.crowdfunding.sessionsejb.DonationEJB;

@Named
@SessionScoped
public class DonationsAdminView implements Serializable {

	private static final long serialVersionUID = 3254181235309041386L;
	private static Logger log = Logger.getLogger(DonationsAdminView.class.getName());
	@Inject
	private DonationEJB donationEJB;

	private Donation donation;

	private double amount;

	private int counter = 0;
	private boolean control = false;

	public DonationsAdminView() {

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

	public List<Donation> getDonations() {
		counter = 0;
		control = false;
		return donationEJB.getDonations();
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getCounter() {
		if (control == false) {
			control = true;
			return ++counter;
		}
		control = false;
		return counter;
	}

	public String displayDonationDescription(Donation donation) {
		setDonation(donation);
		return "donationDescription.xhtml?faces-redirect=true";
	}

	public void modifie() {
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

	public String removeDonation(Donation donation) {
		donationEJB.removeDonation(donation);
		return "donationsList.xhtml?faces-redirect=true";
	}

}
