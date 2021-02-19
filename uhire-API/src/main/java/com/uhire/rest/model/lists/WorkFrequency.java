package com.uhire.rest.model.lists;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class WorkFrequency extends SimpleEnumeration {

	public WorkFrequency(int id) {
		super(id);
	}

	public WorkFrequency() {
		super();
	}

}
