package com.github.rshtishi.app.service.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;

import com.github.rshtishi.app.dto.EventDto;
import com.github.rshtishi.app.entity.Event;

class EventHelperTest {

	@Test
	void testConvertEventDtoToEvent() {
		//setup
		EventDto eventDto = new EventDto(1, "Apache Spark Summit", "Tirana", "2020-09-11", "14:00", "2020-09-11", "18:00");
		//execute
		Event event = EventHelper.convert(eventDto);
		//verify
		assertEquals(eventDto.getId(), event.getId());
		assertEquals(eventDto.getName(), event.getName());
		assertEquals(LocalDateTime.of(2020,9,11,14,0,0),event.getStartsAt());
		assertEquals(LocalDateTime.of(2020,9,11,18,0,0),event.getEndsAt());
	}
	
	@Test
	void testConvertEventToEventDto() {
		//setup
		Event event = new Event("Apache Spark Summit", "Tirana", LocalDateTime.of(2020,9,11,14,0,0), LocalDateTime.of(2020,9,11,18,0,0));
		//execute
		EventDto eventDto = EventHelper.convert(event);
		//verify
		assertEquals(event.getId(),eventDto.getId());
		assertEquals(event.getName(),eventDto.getName());
		assertEquals("2020-09-11",eventDto.getStartDate());
		assertEquals("14:00",eventDto.getStartTime());
		assertEquals("2020-09-11",eventDto.getEndDate());
		assertEquals("18:00",eventDto.getEndTime());
	}

}
