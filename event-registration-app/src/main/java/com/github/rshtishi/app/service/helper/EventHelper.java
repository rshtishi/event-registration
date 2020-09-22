package com.github.rshtishi.app.service.helper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.github.rshtishi.app.dto.EventDto;
import com.github.rshtishi.app.entity.Event;

public class EventHelper {

	public static EventDto convert(Event event) {
		EventDto eventDto = new EventDto();
		eventDto.setId(event.getId());
		eventDto.setName(event.getName());
		eventDto.setPlace(event.getPlace());
		eventDto.setStartDate(event.getStartsAt().toLocalDate().toString());
		eventDto.setStartTime(event.getStartsAt().toLocalTime().toString());
		eventDto.setEndDate(event.getEndsAt().toLocalDate().toString());
		eventDto.setEndTime(event.getEndsAt().toLocalTime().toString());
		return eventDto;
	}

	public static Event convert(EventDto eventDto) {
		String startDateTime = eventDto.getStartDate() + " " + eventDto.getStartTime();
		String endDateTime = eventDto.getEndDate() + " " + eventDto.getEndTime();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
		LocalDateTime startAt = LocalDateTime.parse(startDateTime, formatter);
		LocalDateTime endsAt = LocalDateTime.parse(endDateTime, formatter);
		Event event = new Event();
		event.setId(eventDto.getId());
		event.setName(eventDto.getName());
		event.setPlace(eventDto.getPlace());
		event.setStartsAt(startAt);
		event.setEndsAt(endsAt);
		return event;
	}
}
