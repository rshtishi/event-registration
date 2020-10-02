package com.github.rshtishi.emailservice.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.github.rshtishi.emailservice.entity.EventSubscriber;
import com.github.rshtishi.emailservice.service.EventNotificationService;

@Component
public class EventNewSubscriberConsumer {
	
	@Autowired
	private EventNotificationService eventNotificationService;
	
	@JmsListener(destination = "new.registration")
	public void consumeMessage(EventSubscriber subscriber) {
		//eventNotificationService.notifyEventSubscriberByEmail(subscriber);
		System.out.println(subscriber);
	}

}
