package com.github.rshtishi.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.rshtishi.app.entity.Attendee;
import com.github.rshtishi.app.entity.Event;
import com.github.rshtishi.app.repository.AttendeeRepository;
import com.github.rshtishi.app.repository.EventRepository;

@Service
public class RegisterServiceImpl implements IRegisterService {
	
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private AttendeeRepository attendeeRepository;
	

	@Override
	@Transactional
	public void registerAttendee(Attendee attendee, int eventId) {
		Event event = eventRepository.findById(eventId);
		attendee.setEvent(event);
		attendeeRepository.save(attendee);
	}

}
