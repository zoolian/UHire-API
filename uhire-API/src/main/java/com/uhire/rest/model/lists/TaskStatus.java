package com.uhire.rest.model.lists;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class TaskStatus extends SimpleEnumeration {

	public TaskStatus(int id) {
		super(id);
	}

	public TaskStatus() {
		super();
	}

}
