package com.uhire.rest.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.uhire.rest.model.lists.TaskStatus;

@Document(collection = "task")
public class EmployeeJobFunctionNeed {
	
	@Id
	public String id;
	
	@DBRef
	public Employee employee;
	
	@DBRef
	public JobFunctionNeed need;
	
	@DBRef
	public TaskStatus status;

	public EmployeeJobFunctionNeed(Employee employee, JobFunctionNeed need, TaskStatus status) {
		this.employee = employee;
		this.need = need;
		this.status = status;
	}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

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

	@Override
	public String toString() {
		return "EmployeeJobFunctionNeed [need=" + need.toString() + ", status=" + status.toString() + "]";
	}
	
}
