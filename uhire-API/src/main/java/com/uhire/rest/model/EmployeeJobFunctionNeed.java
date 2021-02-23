package com.uhire.rest.model;

import java.util.ArrayList;
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
	public String id;
	
	@DBRef
	public Employee employee;
	
	@DBRef
	public JobFunctionNeed need;
	
	@DBRef
	public TaskStatus status;
	
	@Autowired
	private static EmployeeJobFunctionNeedRepository employeeJobFunctionNeedRepository;

	public EmployeeJobFunctionNeed(Employee employee, JobFunctionNeed need, TaskStatus status) {
		this.employee = employee;
		this.need = need;
		this.status = status;
	}
	
	public static List<EmployeeJobFunctionNeed> populateEmployeeNeedsFromJobDefaults(Employee employee, List<JobFunctionNeed> needs) {		
		List<EmployeeJobFunctionNeed> newNeedList = new ArrayList<>();
		for(JobFunctionNeed need : needs) {
			EmployeeJobFunctionNeed employeeNeed = new EmployeeJobFunctionNeed(employee, need, new TaskStatus(1));	// hardcoded ID 1
			System.out.println(employeeJobFunctionNeedRepository.findByNeedIdAndEmployeeId(need.getId(), employee.getId()));
			if(!employeeJobFunctionNeedRepository.findByNeedIdAndEmployeeId(need.getId(), employee.getId()).isPresent()) {
				System.out.println(employeeNeed);
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

	@Override
	public String toString() {
		return "EmployeeJobFunctionNeed [id=" + id + ", getEmployee()=" + getEmployee().getId() + ", getNeed()=" + getNeed().getId()
				+ ", getStatus()=" + getStatus().getId() + "]";
	}
}
