package com.github.rshtishi.app.dto;

public class AttendeeDto {

	private String name;
	private String email;
	private String eventName;
	private String registrationId;

	public AttendeeDto() {
		super();
	}

	public AttendeeDto(String name, String email, String eventName, String registrationId) {
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
		return "AttendeeDto [name=" + name + ", email=" + email + ", eventName=" + eventName + ", registrationId="
				+ registrationId + "]";
	}

}
