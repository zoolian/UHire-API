package com.uhire.rest.model.lists;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "EmployeeStatus")
public class EmployeeStatus extends SimpleEnumeration {

	public EmployeeStatus(int id) {
		super(id);
	}

	public EmployeeStatus() {
		super();
	}

}
