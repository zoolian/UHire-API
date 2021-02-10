package com.uhire.rest.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uhire.rest.model.lists.EmployeeStatus;
import com.uhire.rest.model.lists.PayType;
import com.uhire.rest.model.lists.WorkFrequency;
import com.uhire.rest.repository.TaskStatusRepository;

@Document(collection = "person")
public class Employee extends User {
	
	public Employee(String firstName, String lastName, String email, int age, String username,
			boolean enabled, Collection<Role> roles) {
		super(firstName, lastName, email, age, username, enabled, roles);
	}
	
	public Employee(String id, String firstName, String lastName, String email) {
		super(id, firstName, lastName, email);
	}
	
	public Employee() {
		super();
	}

	@DBRef
	private JobPosition position;
	
	private BigDecimal pay;
	
	@DBRef
	private PayType payType;
	
	@DBRef
	private WorkFrequency workFrequency;
	
	private List<EmployeeJobFunctionNeed> needs;
	
	@DBRef
	private EmployeeStatus status;
	
	private TaskStatusRepository taskStatusRepository;
	
	@JsonIgnore
	public boolean isOnboardingComplete() {
		int statusCompletedId = taskStatusRepository.findByName("COMPLETED").getId();
		for(EmployeeJobFunctionNeed need : this.needs) {
			if(need.getStatus().getId() != statusCompletedId) {
				return false;
			}
		}
		return true;
	}

	public JobPosition getPosition() {
		return position;
	}

	public void setPosition(JobPosition position) {
		this.position = position;
	}

	public BigDecimal getPay() {
		return pay;
	}

	public void setPay(BigDecimal pay) {
		this.pay = pay;
	}

	public PayType getPayType() {
		return payType;
	}

	public void setPayType(PayType payType) {
		this.payType = payType;
	}

	public WorkFrequency getWorkFrequency() {
		return workFrequency;
	}

	public void setWorkFrequency(WorkFrequency workFrequency) {
		this.workFrequency = workFrequency;
	}

	public List<EmployeeJobFunctionNeed> getNeeds() {
		return needs;
	}

	public void setNeeds(List<EmployeeJobFunctionNeed> needs) {
		this.needs = needs;
	}

	public EmployeeStatus getStatus() {
		return status;
	}

	public void setStatus(EmployeeStatus status) {
		this.status = status;
	}
	
}
