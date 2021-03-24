package be.helha.crowdfunding.entities;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_groups")
public class Group implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String USERS_GROUP = "users";
	public static final String ADMIN_GROUP = "admin";

	@Id
	private String email;
	private String groupname;

	public Group() {
	}

	public Group(String email, String groupname) {
		this.email = email;
		this.groupname = groupname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

}
