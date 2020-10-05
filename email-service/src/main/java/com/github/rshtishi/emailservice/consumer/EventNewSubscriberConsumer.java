package com.github.rshtishi.emailservice.consumer;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.github.rshtishi.emailservice.entity.EventSubscriber;
import com.github.rshtishi.emailservice.service.EventNotificationService;

@Component
public class EventNewSubscriberConsumer {
	
	@Autowired
	private EventNotificationService eventNotificationService;
	
	@RabbitListener(queues = "new.registration")
	public void consumeMessage(EventSubscriber subscriber) throws Exception {
		//eventNotificationService.notifyEventSubscriberByEmail(subscriber);
		System.out.println(subscriber);
	}

}
