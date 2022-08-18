package com.uhire.rest.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

//@QueryEntity
@Entity
@Table(name = "User")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "position")
	private String username;

	@Column(name = "enabled")
	private boolean enabled = true;
	
	// don't cascade save roles. on the front end, there's no reason to hold all the fields ...
	// they should be changed in the role manager
	@OneToMany(mappedBy = "user")
	private Collection<Role> roles = new ArrayList<>();

	public User(String userId) {
	}

//	@JsonIgnore
//	public Collection<DBRef> getDBRefRoles() {
//		Collection<DBRef> dbRefRoles = new ArrayList<DBRef>();
//		for(Role r : this.getRoles()) {
//			dbRefRoles.add(new DBRef("role", new ObjectId(r.getId())));
//		}
//		return dbRefRoles;
//	}

	public long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Collection<Role> getRoles() {
		return roles;
	}

	public void setRoles(Collection<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User2 [username=" + username + ", enabled=" + enabled + ", roles=" + roles + ", getId()=" + getId()
				+ ", getFirstName()=" + getFirstName() + ", getLastName()=" + getLastName() + ", getEmail()="
				+ getEmail() + ", getDob()=" + getDob() + ", toString()=" + super.toString() + ", getClass()="
				+ getClass() + ", hashCode()=" + hashCode() + "]";
	}	
	
}
