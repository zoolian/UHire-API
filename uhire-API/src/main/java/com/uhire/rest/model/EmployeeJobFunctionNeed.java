package com.uhire.rest.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.uhire.rest.model.lists.TaskStatus;
import com.uhire.rest.repository.EmployeeJobFunctionNeedRepository;

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
	
	private Date createDate = new Date();
	private Date modifyDate = new Date();
	
	@Autowired
	private static EmployeeJobFunctionNeedRepository employeeJobFunctionNeedRepository;

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

	public static List<EmployeeJobFunctionNeed> populateEmployeeNeedsFromJobDefaults(Employee employee, List<JobFunctionNeed> needs, String userId) {
		User user = new User(userId);
		Date date = new Date();
		List<EmployeeJobFunctionNeed> newNeedList = new ArrayList<>();
		for(JobFunctionNeed need : needs) {
			EmployeeJobFunctionNeed employeeNeed = new EmployeeJobFunctionNeed(employee, need, new TaskStatus(1), user, user, date, date);	// hardcoded ID 1
			if(!employeeJobFunctionNeedRepository.findByNeedIdAndEmployeeId(need.getId(), employee.getId()).isPresent()) {
				newNeedList.add(employeeNeed);
			}
		}
		return newNeedList;
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
		return "EmployeeJobFunctionNeed [id=" + id + ", getEmployee()=" + getEmployee().getId() + ", getNeed()=" + getNeed().getId()
				+ ", getStatus()=" + getStatus().getId() + "]";
	}
}
