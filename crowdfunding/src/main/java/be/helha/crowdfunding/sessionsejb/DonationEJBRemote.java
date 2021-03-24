package be.helha.crowdfunding.sessionsejb;

import java.util.List;

import javax.ejb.Remote;

import be.helha.crowdfunding.entities.Donation;
import be.helha.crowdfunding.entities.Project;
import be.helha.crowdfunding.entities.User;

@Remote
public interface DonationEJBRemote {

	public List<Donation> getDonationsByUser(User user);

	public List<Donation> getDonationsByProject(Project project);

	public void detachDonation(Donation donation);

	public Donation findDonationById(int id);

	public List<Donation> getDonations();

	public void removeDonation(Donation donation);

	public Donation updateDonation(Donation donation);

}
