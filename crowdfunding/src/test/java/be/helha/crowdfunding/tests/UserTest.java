package be.helha.crowdfunding.tests;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import be.helha.crowdfunding.entities.Donation;
import be.helha.crowdfunding.entities.Project;
import be.helha.crowdfunding.entities.User;
import be.helha.crowdfunding.util.AuthenticationUtils;
import be.helha.crowdfunding.utils.Explorer;

public class UserTest {
	private User ur1;
	private User ur2;
	private List<Project> projectsUr1;
	private List<Project> projectsUr2;
	private List<Donation> donationsUr1;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		ur1 = new User("cdecr27@hotmail.com", AuthenticationUtils.encodeSHA256("Az+1111111"), "Decroos", "Christophe",
				"0479/00/00/00", "Rue des Postes", "101", "7090", "Braine-Le-Comte", "Belgique");
		ur2 = new User("tomcruise@outlook.com", AuthenticationUtils.encodeSHA256("Az+1111111"), "Cruise", "Tom",
				"0478/00/00/00", "Quai Du Point Jour", "1", "92100", "Boulogne Billancourt", "France");
		projectsUr2 = (List<Project>) Explorer.getField(ur2, "projects");
		projectsUr1 = (List<Project>) Explorer.getField(ur1, "projects");
		donationsUr1 = (List<Donation>) Explorer.getField(ur1, "donations");

		Date date2 = new Date();
		date2.setMonth(2);
		date2.setHours(0);
		date2.setMinutes(0);
		date2.setSeconds(0);

		Project project = new Project("L'EMPREINTE BELGE", "DONNEZ NAISSANCE À LA PREMIÈRE BIÈRE BELGE AUX PANAIS",
				"L’Empreinte‌ ‌Belge lance‌ ‌la‌ ‌première‌ ‌bière‌ ‌au‌ ‌panais. Deux brassins seront financés par cette campagne. L'objectif est de remettre à l'honneur le panais tout en privilégiant le circuit court.",
				"L'Empreinte belge,concept store 100% belge situé à Namur. La boutique propose des produits de créateurs et artisans belges. Héloïse Richard, gérante de l'Empreinte Belge permet de promouvoir les talents de chez nous avec une pointe de créativité.",
				2000., date2, "0470/01/00/00", "l'empreintebelge@outlook.com", "default", "Avenue Jacque Georgin", "2",
				"1030", "Schaerbeek", "Belgique");
		project.setUser(ur2);
		projectsUr2.add(project);

		Donation donation = new Donation(new Date(), 12.25, project);
		donation.setUser(ur1);
		donationsUr1.add(donation);
	}

	@After
	public void tearDown() throws Exception {
		ur1 = null;
		ur2 = null;
		projectsUr1 = null;
		projectsUr2 = null;
		donationsUr1 = null;
	}

	@Test
	public void testGetProjects() {

		List<Project> projects = ur2.getProjects();

		assertTrue(projects.equals(projectsUr2));
	}

	@Test
	public void testGetProject() {

		Date date2 = new Date();
		date2.setMonth(2);
		date2.setHours(0);
		date2.setMinutes(0);
		date2.setSeconds(0);

		Project project = new Project("L'EMPREINTE BELGE", "DONNEZ NAISSANCE À LA PREMIÈRE BIÈRE BELGE AUX PANAIS",
				"L’Empreinte‌ ‌Belge lance‌ ‌la‌ ‌première‌ ‌bière‌ ‌au‌ ‌panais. Deux brassins seront financés par cette campagne. L'objectif est de remettre à l'honneur le panais tout en privilégiant le circuit court.",
				"L'Empreinte belge,concept store 100% belge situé à Namur. La boutique propose des produits de créateurs et artisans belges. Héloïse Richard, gérante de l'Empreinte Belge permet de promouvoir les talents de chez nous avec une pointe de créativité.",
				2000., date2, "0470/01/00/00", "l'empreintebelge@outlook.com", "default", "Avenue Jacque Georgin", "2",
				"1030", "Schaerbeek", "Belgique");

		project.setUser(ur2);

		assertTrue(project.equals(ur2.getProject(0)));
	}

	@Test
	public void testAddProject() {
		Date date = new Date();
		date.setMonth(1);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);

		ur1.addProject("LE P’TIT BEUR, APPEL À L’AIDE",
				"DU DÉSESPOIR À L’ESPOIR, SAUVONS LE P’TIT BEUR, RESTAURANT BIO ET BAR À VIN DE SAINT-GILLES",
				"Le P'ti Beur, restaurant, épicerie et bar à vin, propose une cuisine orientale et méditerranéenne à base de légumes bios. Le P’ti Beur a dû injustement fermer ses portes. Il souhaite aujourd'hui les rouvrir dans un nouveau lieu, grâce à vous.",
				"Après 5 ans de théâtre, Fouad se passionne pour la cuisine. En 1992, il lance La Vie Sauvage. 12 ans après, il fait un tour du monde initiatique. De retour il travaille au : Le colonel, Midi Station, le Wood... de ces expériences naîtra le P’tit Beur.",
				500., date, "0475/00/00/00", "leptitbeur@outlook.com", "default", "Rue de l'Arbre Bénit", "46", "1050",
				"Bruxelles", "Belgique");

		Project project = new Project("LE P’TIT BEUR, APPEL À L’AIDE",
				"DU DÉSESPOIR À L’ESPOIR, SAUVONS LE P’TIT BEUR, RESTAURANT BIO ET BAR À VIN DE SAINT-GILLES",
				"Le P'ti Beur, restaurant, épicerie et bar à vin, propose une cuisine orientale et méditerranéenne à base de légumes bios. Le P’ti Beur a dû injustement fermer ses portes. Il souhaite aujourd'hui les rouvrir dans un nouveau lieu, grâce à vous.",
				"Après 5 ans de théâtre, Fouad se passionne pour la cuisine. En 1992, il lance La Vie Sauvage. 12 ans après, il fait un tour du monde initiatique. De retour il travaille au : Le colonel, Midi Station, le Wood... de ces expériences naîtra le P’tit Beur.",
				500., date, "0475/00/00/00", "leptitbeur@outlook.com", "default", "Rue de l'Arbre Bénit", "46", "1050",
				"Bruxelles", "Belgique");
		project.setUser(ur1);

		assertTrue(projectsUr1.contains(project));
	}

	@Test
	public void testRemoveProject() {
		Project project = projectsUr2.get(0);

		ur2.removeProject(project);
		assertTrue(!projectsUr2.contains(project));
	}

	@Test
	public void testRemoveProjectById() {
		Project project = projectsUr2.get(0);

		ur2.removeProject(0);
		assertTrue(!projectsUr2.contains(project));
	}

	@Test
	public void replaceProject() {
		Date date = new Date();
		date.setMonth(1);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);

		Project project = new Project("OAKSTREET TRIO - LE PREMIER ALBUM",
				"AIDEZ LE TRIO DE JAZZ BELGE OAKSTREET TRIO À PRODUIRE SON PREMIER ALBUM",
				"Oakstreet Trio est un groupe Belge jouant une musique se situant quelque part entre jazz, rock et folk.",
				"Téo Crommen (Guitare & Compositions) Matteo Mazzù (Basse) Guillaume Malempré (Batterie)", 2000., date,
				"0470/22/22/22", "trio@outlook.com", "default", "Rue du Chêne", "25", "7000", "Mons", "Belgique");
		project.setUser(ur2);

		ur2.replaceProject(0, project.getEntitled(), project.getSlogan(), project.getDescription(),
				project.getProjectLeader(), project.getAmount(), project.getEndingDate(), project.getTelephone(),
				project.getEmail(), project.getImageEntitled(), project.getAddress().getStreet(),
				project.getAddress().getStreetNum(), project.getAddress().getPostalCode(),
				project.getAddress().getLocality(), project.getAddress().getCountry());

		assertTrue(projectsUr2.contains(project));
	}

	@Test
	public void testGetDonations() {

		List<Donation> donations = ur1.getDonations();

		assertTrue(donations.equals(donationsUr1));
	}

	@Test
	public void testGetDonation() {

		Donation donation = new Donation(new Date(), 12.25, projectsUr2.get(0));
		donation.setUser(ur1);

		assertTrue(donation.equals(ur1.getDonation(0)) && donation.getAmount() == donationsUr1.get(0).getAmount());
	}

	@Test
	public void testAddDonation() {

		ur1.addDonation(new Date(), 12.25, projectsUr2.get(0));

		Donation donation = new Donation(new Date(), 12.25, projectsUr2.get(0));
		donation.setUser(ur1);

		assertTrue(donationsUr1.contains(donation) && donation.getAmount() == donationsUr1.get(0).getAmount());
	}

	@Test
	public void testRemoveDonation() {

		Donation donation = donationsUr1.get(0);

		ur1.removeDonation(0);
		assertTrue(!donationsUr1.contains(donation));
	}

	@Test
	public void testRemoveDonationById() {
		Donation donation = donationsUr1.get(0);

		ur1.removeDonation(donation);
		assertTrue(!donationsUr1.contains(donation));
	}

	@Test
	public void replaceDonation() {

		Donation donation = new Donation(new Date(), 122.25, projectsUr2.get(0));
		donation.setUser(ur1);

		assertFalse(donationsUr1.contains(donation) && donation.getAmount() == donationsUr1.get(0).getAmount());

		ur1.replaceDonation(donation.getDate(), donation.getAmount(), donation.getProject());

		assertTrue(donationsUr1.contains(donation) && donation.getAmount() == donationsUr1.get(0).getAmount());
	}

}
