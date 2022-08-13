package com.uhire.rest.model;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "User")
public class JobFunctionNeed {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "noticeRecipients")
	private List<Person> noticeRecipients = new ArrayList<>();

	@Column(name = "enabled")
	private boolean enabled = true;

	public long getId() {
		return id;
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
