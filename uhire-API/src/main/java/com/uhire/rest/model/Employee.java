package com.uhire.rest.model;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uhire.rest.EmployeeStatus;
import com.uhire.rest.PayType;
import com.uhire.rest.TaskStatus;
import com.uhire.rest.WorkFrequency;

@Document(collection = "person")
public class Employee extends User {
	
	public Employee(String firstName, String lastName, String email, int age, String username,
			boolean enabled, Collection<Role> roles) {
		super(firstName, lastName, email, age, username, enabled, roles);
		// TODO Auto-generated constructor stub
	}

	@DBRef
	private JobPosition position;
	
	private BigDecimal pay;
	
	private PayType payType;
	
	private WorkFrequency workFrequency;
	
	private List<EmployeeJobFunctionNeed> needs;
	
	private EmployeeStatus status;
	
	@JsonIgnore
	public boolean isOnboardingComplete() {
		for(EmployeeJobFunctionNeed need : this.needs) {
			if(need.getStatus() != TaskStatus.COMPLETED) {
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
