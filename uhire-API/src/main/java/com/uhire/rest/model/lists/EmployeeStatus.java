package com.uhire.rest.model.lists;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class EmployeeStatus extends SimpleEnumeration {

	public EmployeeStatus(int id) {
		super(id);
	}

	public EmployeeStatus() {
		super();
	}

}
