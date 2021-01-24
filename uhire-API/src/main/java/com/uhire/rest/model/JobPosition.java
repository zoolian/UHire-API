package com.uhire.rest.model;

import java.math.BigDecimal;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.uhire.rest.PayType;
import com.uhire.rest.WorkFrequency;

@Document
public class JobPosition {

	@Id
	private String id;
	
	private String title;
	
	private String description;
	
	private BigDecimal maxPay;
	
	private BigDecimal defaultPay;
	
	private PayType defaultPayType;
	
	private WorkFrequency defaultWorkFrequency;

	public String getId() {
		return id;
	}

	public void setId(String id) {
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
	
}
