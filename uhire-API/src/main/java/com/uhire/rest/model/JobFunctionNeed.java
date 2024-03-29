package com.uhire.rest.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class JobFunctionNeed {
	
	@Id
	private String id;
	
	private String name;
	
	private String description;
	
	private List<Person> noticeRecipients = new ArrayList<>();
	
	private boolean enabled = true;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Person> getNoticeRecipients() {
		return noticeRecipients;
	}

	public void setNoticeRecipients(List<Person> noticeRecipients) {
		this.noticeRecipients = noticeRecipients;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String toString() {
		return "JobFunctionNeed [id=" + id + ", name=" + name + ", description=" + description + ", noticeRecipients="
				+ noticeRecipients + ", enabled=" + enabled + "]";
	}
	
}
