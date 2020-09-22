package com.github.rshtishi.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rshtishi.app.dto.EventDto;
import com.github.rshtishi.app.repository.EventRepository;
import com.github.rshtishi.app.service.helper.EventHelper;

@Service
public class EventService {
	
	@Autowired
	private EventRepository repository;
	
	public List<EventDto> findAllEvents(){
		return null;
	}
	
	

}
