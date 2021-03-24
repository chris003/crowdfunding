package be.helha.crowdfunding.util;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import be.helha.crowdfunding.entities.Address;
import be.helha.crowdfunding.entities.Donation;
import be.helha.crowdfunding.entities.Group;
import be.helha.crowdfunding.entities.Project;
import be.helha.crowdfunding.entities.User;

public class MainDataCreation {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		User admin = null;
		try {
			admin = new User("admin@demoCrowdfunding.be", AuthenticationUtils.encodeSHA256("Az+1111111"), "Smith",
					"Will", "0470/00/00/00", "Chaussée de Binche", "159", "7000", "Mons", "Belgique");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Group group = new Group();
		group.setEmail(admin.getEmail());
		group.setGroupname(Group.ADMIN_GROUP);

		User ur1 = null;
		try {
			ur1 = new User("user@outlooktest.com", AuthenticationUtils.encodeSHA256("Az+1111111"), "Theron",
					"Charlize", "0479/00/00/00", "Rue des Postes", "101", "7090", "Braine-Le-Comte", "Belgique");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User ur2 = null;
		try {
			ur2 = new User("tomcruise@outlook.com", AuthenticationUtils.encodeSHA256("Az+1111111"), "Cruise", "Tom",
					"0478/00/00/00", "Quai Du Point Jour", "1", "92100", "Boulogne Billancourt", "France");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User ur3 = null;
		try {
			ur3 = new User("sharonstone@outlook.com", AuthenticationUtils.encodeSHA256("Az+1111111"), "Stone", "Sharon",
					"0472/11/00/00", "Quai Du Point Nuit", "5", "92100", "Brest", "France");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		User ur4 = null;
		try {
			ur4 = new User("jeanpierrefoucault@outlook.com", AuthenticationUtils.encodeSHA256("Az+1111111"), "Foucault",
					"Jean-Pierre", "0472/19/00/00", "Quai Du Point Midi", "7", "92100", "Lille", "France");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		Date date = new Date();
		Date date2 = new Date();
		date.setMonth(5);
		date2.setMonth(4);
		date.setHours(0);
		date.setMinutes(0);
		date.setSeconds(0);
		date2.setHours(0);
		date2.setMinutes(0);
		date2.setSeconds(0);

		ur1.addProject("Le P’tit Bar, appel à l’aide",
				"Du déséspoir à l'espoir, sauvons le P’tit Bar, restaurant bio et bar à vin de Sivry",
				"Le P'ti Bar, restaurant, épicerie et bar à vin, propose une cuisine orientale et méditerranéenne à base de légumes bios. Le P’ti Bar a dû injustement fermer ses portes. Il souhaite aujourd'hui les rouvrir dans un nouveau lieu, grâce à vous.",
				"Après 5 ans de théâtre, Mike se passionne pour la cuisine. En 1992, il lance La Vie Sauvage. 12 ans après, il fait un tour du monde initiatique. De retour il travaille au : Quick, Duchemin, Tricatel... de ces expériences naîtra le P’tit Bar.",
				500., date, "0475/00/00/00", "leptitbar@outlook.com", "default.jpg", "Rue de l'Arbre Bénit", "46",
				"1050", "Sivry", "Belgique");
		ur2.addProject("La Brasserie du Coin", "Donnez naissance à la première bière belge aux panais",
				"Deux brassins seront financés par cette campagne. L'objectif est de remettre à l'honneur le panais tout en privilégiant le circuit court.",
				"La Brasserie du Coin, concept store 100% belge situé à Mons. La boutique propose des produits de créateurs et artisans belges. Claire Chazal, gérante de l'Empreinte Belge permet de promouvoir les talents de chez nous avec une pointe de créativité.",
				2000., date2, "0470/01/00/00", "labrasserieducoin@outlook.com", "default.jpg", "Avenue Jacque Georgin",
				"2", "1030", "Schaerbeek", "Belgique");
		ur2.addProject("Les Vieux Fourneaux - Le premier album",
				"Aidez le trio de jazz belge Les Vieux Fourneaux à produire son premier album",
				"Les Vieux Fourneaux est un groupe Belge jouant une musique se situant quelque part entre jazz, rock et folk.",
				"Pierre Richard (Guitare & Compositions) Roland Giraud (Basse) Eddy Mitchell (Batterie)", 2000., date,
				"0470/22/22/22", "lesvieuxfourneaux@outlook.com", "default.jpg", "Rue du Chêne", "25", "7000", "Mons", "Belgique");
		ur3.addProject("La Boîte à Manger",
				"Contribuez à l'ouverture de notre épicerie de produits en vrac locaux et raisonnés, à Ostende",
				"La boîte à Manger, c'est l'épicerie qui alliera le local, le raisonné, la qualité et le service avec le sourire!",
				"Olivia et toute sa famille voulaient mettre en avant les produits locaux, raisonnés et abordables, c'est fait!",
				3000.00, date, "0470/25/00/00", "boiteamanger@outlook.com", "default.jpg", "Rue de la victoire", "21", "7060",
				"Soignies", "Belgique");

		ur2.addDonation(new Date(), 1225.25, ur1.getProject(0));
		ur1.addDonation(new Date(), 120.25, ur2.getProject(0));
		ur1.addDonation(new Date(), 126.25, ur3.getProject(0));

		Group group2 = new Group();
		group2.setEmail(ur1.getEmail());
		group2.setGroupname(Group.USERS_GROUP);
		Group group3 = new Group();
		group3.setEmail(ur2.getEmail());
		group3.setGroupname(Group.USERS_GROUP);
		Group group4 = new Group();
		group4.setEmail(ur3.getEmail());
		group4.setGroupname(Group.USERS_GROUP);
		Group group5 = new Group();
		group5.setEmail(ur4.getEmail());
		group5.setGroupname(Group.USERS_GROUP);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("crowdfunding_LOCAL");
		EntityManager em = emf.createEntityManager();

		EntityTransaction tx = em.getTransaction();

		tx.begin();

		em.persist(admin);
		em.persist(group);

		em.persist(ur1);
		em.persist(group2);

		em.persist(ur2);
		em.persist(group3);

		em.persist(ur3);
		em.persist(group4);

		em.persist(ur4);
		em.persist(group5);

		tx.commit();

		em.close();
		emf.close();
	}
}
