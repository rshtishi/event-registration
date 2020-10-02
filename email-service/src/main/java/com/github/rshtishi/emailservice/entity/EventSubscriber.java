package com.github.rshtishi.emailservice.entity;

import java.io.Serializable;

public class EventSubscriber implements Serializable {

	private static final long serialVersionUID = 1L;

	private String name;
	private String email;
	private String eventName;
	private String registrationId;

	public EventSubscriber() {
		super();
	}

	public EventSubscriber(String name, String email, String eventName, String registrationId) {
		super();
		this.name = name;
		this.email = email;
		this.eventName = eventName;
		this.registrationId = registrationId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getRegistrationId() {
		return registrationId;
	}

	public void setRegistrationId(String registrationId) {
		this.registrationId = registrationId;
	}

	@Override
	public String toString() {
		return "EventSubscriber [name=" + name + ", email=" + email + ", eventName=" + eventName + ", registrationId="
				+ registrationId + "]";
	}

}
