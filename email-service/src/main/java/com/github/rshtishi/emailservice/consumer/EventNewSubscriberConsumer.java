package com.github.rshtishi.emailservice.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import com.github.rshtishi.emailservice.entity.EventSubscriber;

@Component
public class EventNewSubscriberConsumer {
	
	@JmsListener(destination = "new.registration")
	public void consumeMessage(EventSubscriber message) {
		System.out.println(">>>>>> :"+message);
	}

}
