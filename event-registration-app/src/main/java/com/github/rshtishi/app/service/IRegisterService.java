package com.github.rshtishi.app.service;

import com.github.rshtishi.app.entity.Attendee;

public interface IRegisterService {

	void registerAttendee(Attendee attendee, int eventId);

}