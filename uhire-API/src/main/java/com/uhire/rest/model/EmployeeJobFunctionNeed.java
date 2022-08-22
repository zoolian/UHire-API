package com.uhire.rest.model;

import java.util.Date;
import org.springframework.data.annotation.Id;

import com.uhire.rest.model.lists.TaskStatus;

import javax.persistence.*;

@Entity
@Table(name = "employeeJobFunctionNeed")
public class EmployeeJobFunctionNeed {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;


	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "jobFunctionNeed_id")
	private JobFunctionNeed need;

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name = "taskStatus_id")
	private TaskStatus status;

	@Column(name = "createUser")
	private User createUser;

	@Column(name = "modifyUser")
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
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
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
