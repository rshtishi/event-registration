package com.github.rshtishi.app.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.github.rshtishi.app.dto.AttendeeDto;

@Component
public class RegistrationEventProducer {

	@Autowired
	private KafkaTemplate<String, AttendeeDto> kafkaTemplate;

	@Value("${spring.kafka.template.default-topic}")
	private String destination;

	public void sendTo(AttendeeDto attendee) {
		kafkaTemplate.send(destination, attendee);
	}

}
