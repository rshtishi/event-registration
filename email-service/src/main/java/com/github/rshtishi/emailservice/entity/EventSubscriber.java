package com.github.rshtishi.emailservice.entity;

import java.io.Serializable;

public class EventSubscriber implements Serializable{

	private String subscriberName;
	private String subscriberEmail;
	private String eventName;
	private String subcriberIdentificationNo;

	public EventSubscriber() {
		super();
	}

	public EventSubscriber(String subscriberName, String subscriberEmail, String eventName,
			String subcriberIdentificationNo) {
		super();
		this.subscriberName = subscriberName;
		this.subscriberEmail = subscriberEmail;
		this.eventName = eventName;
		this.subcriberIdentificationNo = subcriberIdentificationNo;
	}

	public String getSubscriberName() {
		return subscriberName;
	}

	public void setSubscriberName(String subscriberName) {
		this.subscriberName = subscriberName;
	}

	public String getSubscriberEmail() {
		return subscriberEmail;
	}

	public void setSubscriberEmail(String subscriberEmail) {
		this.subscriberEmail = subscriberEmail;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getSubcriberIdentificationNo() {
		return subcriberIdentificationNo;
	}

	public void setSubcriberIdentificationNo(String subcriberIdentificationNo) {
		this.subcriberIdentificationNo = subcriberIdentificationNo;
	}

	@Override
	public String toString() {
		return "EventSubscriber [subscriberName=" + subscriberName + ", subscriberEmail=" + subscriberEmail
				+ ", eventName=" + eventName + ", subcriberIdentificationNo=" + subcriberIdentificationNo + "]";
	}

}
