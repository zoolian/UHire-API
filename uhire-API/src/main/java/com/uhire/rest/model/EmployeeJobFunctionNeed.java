package com.uhire.rest.model;

import org.springframework.data.mongodb.core.mapping.DBRef;

import com.uhire.rest.TaskStatus;

public class EmployeeJobFunctionNeed {
	
//	@DBRef
//	public Employee employee;
	
	@DBRef
	public JobFunctionNeed need;
	
	public TaskStatus status = TaskStatus.NEW;

//	public String getId() {
//		return id;
//	}
//
//	public void setId(String id) {
//		this.id = id;
//	}
//
//	public Employee getEmployee() {
//		return employee;
//	}
//
//	public void setEmployee(Employee employee) {
//		this.employee = employee;
//	}

	public JobFunctionNeed getNeed() {
		return need;
	}

	public void setNeed(JobFunctionNeed need) {
		this.need = need;
	}

	public TaskStatus getStatus() {
		return status;
	}

	public void setStatus(TaskStatus status) {
		this.status = status;
	}
	
}
