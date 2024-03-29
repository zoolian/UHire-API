package com.uhire.rest.model;

import java.util.Date;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.uhire.rest.model.lists.TaskStatus;

@Document(collection = "task")
public class EmployeeJobFunctionNeed {
	
	@Id
	private String id;
	
	@DBRef
	private Employee employee;
	
	@DBRef
	private JobFunctionNeed need;
	
	@DBRef
	private TaskStatus status;
	
	@DBRef
	private User createUser;
	
	@DBRef
	private User modifyUser;
	
	private Date createDate; // This one needs to be set on the front end, otherwise it will get set to "now" every time.
	private Date modifyDate = new Date();	// This one is set to "now" every time.

	public EmployeeJobFunctionNeed() {
		super();
	}

	public EmployeeJobFunctionNeed(Employee employee, JobFunctionNeed need, TaskStatus status) {
		this.employee = employee;
		this.need = need;
		this.status = status;
	}
	
	public EmployeeJobFunctionNeed(Employee employee, JobFunctionNeed need, TaskStatus status,
			User createUser, User modifyUser, Date createDate, Date modifyDate) {
		super();
		this.employee = employee;
		this.need = need;
		this.status = status;
		this.createUser = createUser;
		this.modifyUser = modifyUser;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
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

	public User getCreateUser() {
		return createUser;
	}

	public void setCreateUser(User createUser) {
		this.createUser = createUser;
	}

	public User getModifyUser() {
		return modifyUser;
	}

	public void setModifyUser(User modifyUser) {
		this.modifyUser = modifyUser;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	@Override
	public String toString() {
		return "EmployeeJobFunctionNeed [id=" + id + ", createDate=" + createDate + ", modifyDate=" + modifyDate
				+ ", getEmployee()=" + getEmployee().getId() + ", getNeed()=" + getNeed().getId() + ", getStatus()=" + getStatus().getId()
				+ ", getCreateUser()=" + getCreateUser().getId() + ", getModifyUser()=" + getModifyUser().getId() + "]";
	}
}
