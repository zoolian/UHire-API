package com.uhire.rest.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.uhire.rest.model.lists.PayType;
import com.uhire.rest.model.lists.WorkFrequency;

@Document
public class JobPosition {

	@Id
	private long id;
	
	private String title;
	
	private String description;
	
	private BigDecimal maxPay;
	
	private BigDecimal defaultPay;
	
	@DBRef
	private PayType defaultPayType;
	
	@DBRef
	private WorkFrequency defaultWorkFrequency;
	
	@DBRef
	private List<JobFunctionNeed> defaultNeeds = new ArrayList<>();
	
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
