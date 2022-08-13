package com.uhire.rest.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.uhire.rest.model.lists.EmployeeStatus;
import com.uhire.rest.model.lists.PayType;
import com.uhire.rest.model.lists.WorkFrequency;
import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "employee")
public class Employee {
	
	public Employee() {
		super();
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "position")
	private JobPosition position;
	
//	@Column(name = "")
//	private Department department;
	
	@Column(name = "manager")
	private Employee manager;

	@Column(name = "joinDate")
	private LocalDate joinDate = LocalDate.now();

	@Column(name = "termDate")
	private LocalDate termDate = LocalDate.now();

	@Column(name = "pay")
	private BigDecimal pay;
	
	@Column(name = "payType")
	private PayType payType;
	
	@Column(name = "workFrequency")
	private WorkFrequency workFrequency;
	
	@Column(name = "status")
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

	public long getId() {
		return id;
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
