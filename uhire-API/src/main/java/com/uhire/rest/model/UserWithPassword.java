package com.uhire.rest.model;

import java.util.ArrayList;
import java.util.Collection;

public class UserWithPassword extends Person {
	
	public UserWithPassword(String firstName, String lastName, String email, int age) {
		super(firstName, lastName, email, age);
	}

	private String username;
	
	private String password;
	
	private boolean enabled;

	private Collection<Role> roles = new ArrayList<Role>();

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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
		return "UserWithPassword [username=" + username + ", password=" + password + ", enabled=" + enabled + ", roles="
				+ roles + ", getId()=" + getId() + ", getFirstName()=" + getFirstName() + ", getLastName()="
				+ getLastName() + ", getEmail()=" + getEmail() + ", getAge()=" + getAge() + ", toString()="
				+ super.toString() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + "]";
	}	
	
}
