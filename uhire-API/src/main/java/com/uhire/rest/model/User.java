package com.uhire.rest.model;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Set;

import org.springframework.data.annotation.Id;

import javax.persistence.*;

//@QueryEntity
@Entity
@Table(name = "user")
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
	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name = "UserRole",
			joinColumns = {@JoinColumn(name = "user_ID", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "role_ID", referencedColumnName = "id")})
	private Set<Role> roles;

	public User(long userId) {
	}

    public User(String firstName, String lastName, String email, LocalDate dob, String username, boolean b) {
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

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@Override
	public String toString() {
		return "User{" +
				"id=" + id +
				", username='" + username + '\'' +
				", enabled=" + enabled +
				", roles=" + roles +
				'}';
	}
}
