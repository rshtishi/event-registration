package com.github.rshtishi.app.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.rshtishi.app.dto.EventDto;
import com.github.rshtishi.app.service.IEventService;

@Controller
@RequestMapping("/events")
public class EventController {

	@Autowired
	private IEventService eventService;

	@GetMapping("/new")
	public String showNewEventForm(Model model) {
		model.addAttribute("eventDto", new EventDto());
		return "new-event";
	}

	@PostMapping("/new")
	public String addEvent(@Valid EventDto eventDto, Errors errors, Model model) {
		if (errors.hasErrors()) {
			return "new-event";
		}
		eventService.save(eventDto);
		return "redirect:/events";
	}

	@GetMapping
	public String showEvents(Model model) {
		List<EventDto> events = eventService.findAllEvents();
		model.addAttribute("events", events);
		return "events";
	}


}
