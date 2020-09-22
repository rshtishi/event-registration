package com.github.rshtishi.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/events")
public class EventController {
	
	@GetMapping("/new")
	public String showNewRecipeForm() {
		return "new-event";
	}

}
