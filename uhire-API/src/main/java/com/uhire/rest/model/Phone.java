package com.uhire.rest.model;

import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "Phone")
public class Phone {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "country")
	private String country = "+1";

	@Column(name = "area")
	private String area;

	@Column(name = "subscriber")
	private String subscriber;

	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getSubscriber() {
		return subscriber;
	}

	public void setSubscriber(String subscriber) {
		this.subscriber = subscriber;
	}

	@Override
	public String toString() {
		return country + "-" + area + "-" + subscriber.substring(0, 3) + "-" + subscriber.substring(3, subscriber.length());
	}
	
	
}
