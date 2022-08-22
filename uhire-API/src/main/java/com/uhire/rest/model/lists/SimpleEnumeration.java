package com.uhire.rest.model.lists;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;

public class SimpleEnumeration {
	@Id
	long id;

	@Column(name = "name")
	String name;

	public SimpleEnumeration() {
		super();
	}

	public SimpleEnumeration(int id) {
		this.id = id;
	}

	public int getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "SimpleEnumeration [id=" + id + ", name=" + name + "]";
	}
	
}
