package com.github.rshtishi.app.producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.rshtishi.app.dto.AttendeeDto;

@Component
public class RegistrationEventProducer {

	@Autowired
	private RabbitTemplate rabbitTemplate;
	@Value("${rabbitmq.routing-key}")
	private String destination ;
	
    public void sendTo(AttendeeDto attendee) {
        rabbitTemplate.convertAndSend(destination, attendee);
    }

}
