package com.github.rshtishi.app.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.github.rshtishi.app.dto.AttendeeDto;

@Component
public class RegistrationEventProducer {

	@Autowired
	private JmsTemplate jmsTemplate;
	@Value("activemq.queue")
	private String destination ;
	
    public void sendTo(AttendeeDto attendee) {
        jmsTemplate.convertAndSend(destination, attendee);
    }

}
