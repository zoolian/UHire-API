package com.uhire.rest.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uhire.rest.model.lists.EmployeeStatus;
import com.uhire.rest.model.lists.PayType;
import com.uhire.rest.model.lists.WorkFrequency;

@Document(collection = "person")
public class Employee extends User {
	
	public Employee(String firstName, String lastName, String email, LocalDate dob, String username,
			boolean enabled, Collection<Role> roles) {
		super(firstName, lastName, email, dob, username, enabled, roles);
	}
	
	public Employee(String id, String firstName, String lastName, String email) {
		super(id, firstName, lastName, email);
	}
	
	public Employee() {
		super();
	}

	@DBRef
	private JobPosition position;
	
//	@DBRef
//	private Department department;
	
	@DBRef
	private Employee manager;
	
	private LocalDate joinDate = LocalDate.now();
	
	private LocalDate termDate = LocalDate.now();
	
	private BigDecimal pay;
	
	@DBRef
	private PayType payType;
	
	@DBRef
	private WorkFrequency workFrequency;
	
	@DBRef
	private EmployeeStatus status;
	
	@JsonIgnore
	public boolean isOnboardingComplete(List<EmployeeJobFunctionNeed> needs, int statusCompletedId) {
		for(EmployeeJobFunctionNeed need : needs) {
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

	public Employee getManager() {
		return manager;
	}

	public void setManager(Employee manager) {
		this.manager = manager;
	}

	public LocalDate getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(LocalDate joinDate) {
		this.joinDate = joinDate;
	}

	public LocalDate getTermDate() {
		return termDate;
	}

	public void setTermDate(LocalDate termDate) {
		this.termDate = termDate;
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

	public EmployeeStatus getStatus() {
		return status;
	}

	public void setStatus(EmployeeStatus status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "Employee [id=" + getId() + ", pay=" + pay + ", getPosition()=" + getPosition().getTitle() + ", getPayType()=" + getPayType().getName()
				+ ", getWorkFrequency()=" + getWorkFrequency().getName() + ", getStatus()=" + getStatus().getName() + "]";
	}
}
