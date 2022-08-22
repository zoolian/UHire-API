package com.uhire.rest.model.lists;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PayType")
public class PayType extends SimpleEnumeration {

	public PayType(int id) {
		super(id);
	}

	public PayType() {
		super();
	}

}
