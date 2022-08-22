package com.uhire.rest.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "password")
public class Password {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "personId")
	private String personId;

	@Column(name = "password")
	private String password;

	public Password(long personId, String password) {
		this.personId = personId;
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUserId() {
		return personId;
	}

	public void setUserId(String personId) {
		this.personId = personId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "Password [id=" + id + ", personId=" + personId + ", password=" + password + "]";
	}
	
	
}
