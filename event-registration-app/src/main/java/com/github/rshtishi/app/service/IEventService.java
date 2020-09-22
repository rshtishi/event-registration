package com.github.rshtishi.app.service;

import java.util.List;

import com.github.rshtishi.app.dto.EventDto;
import com.github.rshtishi.app.entity.Event;

public interface IEventService {

	List<EventDto> findAllEvents();

	Event save(EventDto eventDto);

}