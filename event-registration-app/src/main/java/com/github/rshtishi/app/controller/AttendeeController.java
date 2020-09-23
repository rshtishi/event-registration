package com.github.rshtishi.app.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.rshtishi.app.entity.Attendee;
import com.github.rshtishi.app.service.IRegisterService;

@Controller
@RequestMapping("/attendee")
public class AttendeeController {
	
	@Autowired
	private IRegisterService registerService;
	
	@PostMapping("/new")
	public String addAttendee(@Valid Attendee attendee, Errors errors, HttpSession session) {
		if (errors.hasErrors()) {
			return "new-event";
		}
		Integer eventId = (Integer) session.getAttribute("eventId");
		registerService.registerAttendee(attendee, eventId);
		return "redirect:/events";
	}

}
