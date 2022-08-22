package com.uhire.rest.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import com.uhire.rest.model.lists.PayType;
import com.uhire.rest.model.lists.WorkFrequency;

import javax.persistence.*;

@Entity
@Table(name = "jobPosition")
public class JobPosition {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "description")
	private String description;

	@Column(name = "maxPay")
	private BigDecimal maxPay;

	@Column(name = "defaultPay")
	private BigDecimal defaultPay;

	@OneToOne
	@JoinColumn(name="defaultPayType_id")
	private PayType defaultPayType;

	@OneToOne
	@JoinColumn(name="defaultWorkFrequency_id")
	private WorkFrequency defaultWorkFrequency;

	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
	@JoinTable(name = "defaultJobPositionNeeds",
			joinColumns = {@JoinColumn(name = "jobPosition_id", referencedColumnName = "id")},
			inverseJoinColumns = {@JoinColumn(name = "need_id", referencedColumnName = "id")})
	private List<JobFunctionNeed> defaultNeeds;
	
	private boolean enabled = true;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public BigDecimal getMaxPay() {
		return maxPay;
	}

	public void setMaxPay(BigDecimal maxPay) {
		this.maxPay = maxPay;
	}

	public BigDecimal getDefaultPay() {
		return defaultPay;
	}

	public void setDefaultPay(BigDecimal defaultPay) {
		this.defaultPay = defaultPay;
	}

	public PayType getDefaultPayType() {
		return defaultPayType;
	}

	public void setDefaultPayType(PayType defaultPayType) {
		this.defaultPayType = defaultPayType;
	}

	public WorkFrequency getDefaultWorkFrequency() {
		return defaultWorkFrequency;
	}

	public void setDefaultWorkFrequency(WorkFrequency defaultWorkFrequency) {
		this.defaultWorkFrequency = defaultWorkFrequency;
	}

	public List<JobFunctionNeed> getDefaultNeeds() {
		return defaultNeeds;
	}

	public void setDefaultNeeds(List<JobFunctionNeed> defaultNeeds) {
		this.defaultNeeds = defaultNeeds;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
}
