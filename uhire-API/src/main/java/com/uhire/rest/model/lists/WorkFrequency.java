package com.uhire.rest.model.lists;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "WorkFrequency")
public class WorkFrequency extends SimpleEnumeration {

	public WorkFrequency(int id) {
		super(id);
	}

	public WorkFrequency() {
		super();
	}

}
