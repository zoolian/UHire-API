//*****************************************************************************
// ID 1 is hard coded as the only value that allows delete operation to be applied,
// as this is the only state in which the task request hasn't been sent yet.
// ID 2 is hard coded as pending.
// ********************************************************************************
package com.uhire.rest.model.lists;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "TaskStatus")
public class TaskStatus extends SimpleEnumeration {

	public TaskStatus(int id) {
		super(id);
	}

	public TaskStatus() {
		super();
	}

}
