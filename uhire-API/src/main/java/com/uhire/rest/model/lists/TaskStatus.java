//*****************************************************************************
// ID 1 is hardcoded as the only value that allows delete operation to be applied,
// as this is the only state in which the request hasn't been sent yet.
// ********************************************************************************
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
