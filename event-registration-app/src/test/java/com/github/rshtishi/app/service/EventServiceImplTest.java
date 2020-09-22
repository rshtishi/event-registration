package com.github.rshtishi.app.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.github.rshtishi.app.dto.EventDto;
import com.github.rshtishi.app.entity.Event;
import com.github.rshtishi.app.repository.EventRepository;
import static org.mockito.Mockito.any;

class EventServiceImplTest {

	@InjectMocks
	private EventServiceImpl eventService;
	@Mock
	private EventRepository repository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testFindAllEvents() {
		// setup
		Event event = new Event("Apache Spark Summit", "Tirana", LocalDateTime.of(2020, 9, 11, 14, 0, 0),
				LocalDateTime.of(2020, 9, 11, 18, 0, 0));
		List<Event> events = new ArrayList<>();
		events.add(event);
		when(repository.findAll()).thenReturn(events);
		// execute
		List<EventDto> returnedEvents = eventService.findAllEvents();
		// verify
		assertEquals(event.getId(), returnedEvents.get(0).getId());
	}

	@Test
	void testSave() {
		// setup
		EventDto eventDto = new EventDto(1, "Apache Spark Summit", "Tirana", "2020-09-11", "14:00", "2020-09-11", "18:00");
		Event event = new Event("Apache Spark Summit", "Tirana", LocalDateTime.of(2020,9,11,14,0,0), LocalDateTime.of(2020,9,11,18,0,0));
		when(repository.save(any())).thenReturn(event);
		// execute
		Event returnedEvent = eventService.save(eventDto);
		// verify
		assertEquals(event.getId(), returnedEvent.getId());
	}

}
