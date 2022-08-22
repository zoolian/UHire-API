package com.uhire.rest.model;

import java.time.LocalDate;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "Person")
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "firstName")
	private String firstName;

	@Column(name = "lastName")
	private String lastName;
	
	//private Phone phone;
	//private Address address;
	@Column(name = "email")
	private String email;

	@Column(name = "dob")
	private LocalDate dob = LocalDate.now();

	public Person() {}
	
	public Person(String firstName, String lastName, String email, LocalDate dob) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.dob = dob;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) { this.id = id; }

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

//	public Phone getPhone() {
//		return phone;
//	}
//	public void setPhone(Phone phone) {
//		this.phone = phone;
//	}

	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public LocalDate getDob() {
		return dob;
	}

	public void setDob(LocalDate dob) {
		this.dob = dob;
	}
	
	@Override
	public String toString() {
		return "Person [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email
				+ "]";
	}
}
