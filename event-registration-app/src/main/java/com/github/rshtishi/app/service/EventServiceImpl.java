package com.github.rshtishi.app.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.rshtishi.app.dto.EventDto;
import com.github.rshtishi.app.entity.Event;
import com.github.rshtishi.app.repository.EventRepository;
import com.github.rshtishi.app.service.helper.EventHelper;

@Service
public class EventServiceImpl implements IEventService {

	@Autowired
	private EventRepository repository;

	@Override
	public List<EventDto> findAllEvents() {
		return repository.findAll().parallelStream().map(EventHelper::convert).collect(Collectors.toList());
	}

	@Override
	public Event save(EventDto eventDto) {
		Event event = EventHelper.convert(eventDto);
		return repository.save(event);
	}

}
