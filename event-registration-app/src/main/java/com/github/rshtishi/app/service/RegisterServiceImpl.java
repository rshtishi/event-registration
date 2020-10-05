package com.github.rshtishi.app.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.rshtishi.app.entity.Attendee;
import com.github.rshtishi.app.entity.Event;
import com.github.rshtishi.app.producer.RegistrationEventProducer;
import com.github.rshtishi.app.repository.AttendeeRepository;
import com.github.rshtishi.app.repository.EventRepository;
import com.github.rshtishi.app.service.helper.AttendeeHelper;

@Service
public class RegisterServiceImpl implements IRegisterService {
	
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private AttendeeRepository attendeeRepository;
	@Autowired
	private RegistrationEventProducer producer;
	

	@Override
	@Transactional
	public void registerAttendee(Attendee attendee, int eventId) {
		Event event = eventRepository.findById(eventId);
		attendee.setEvent(event);
		attendee.setRegistrationId(UUID.randomUUID().toString());
		attendeeRepository.save(attendee);
		//producer.sendTo(AttendeeHelper.convert(attendee));
	}

}
