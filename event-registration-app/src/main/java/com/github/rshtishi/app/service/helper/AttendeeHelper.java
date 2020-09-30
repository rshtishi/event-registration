package com.github.rshtishi.app.service.helper;

import com.github.rshtishi.app.dto.AttendeeDto;
import com.github.rshtishi.app.entity.Attendee;

public class AttendeeHelper {
	
	public static AttendeeDto convert(Attendee attendee) {
		AttendeeDto attendeeDto = new AttendeeDto();
		attendeeDto.setName(attendee.getFullName());
		attendeeDto.setEmail(attendee.getEmail());
		attendeeDto.setEventName(attendee.getEvent().getName());
		attendeeDto.setRegistrationId(attendee.getRegistrationId());
		return attendeeDto;
	}

}
