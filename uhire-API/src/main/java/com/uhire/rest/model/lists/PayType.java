package com.uhire.rest.model.lists;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class PayType extends SimpleEnumeration {

	public PayType(int id) {
		super(id);
	}

	public PayType() {
		super();
	}

}
