package com.github.rshtishi.app.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.github.rshtishi.app.entity.Event;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EventRepositoryTest {

	@Autowired
	private EventRepository eventRepository;

	@Test
	@Order(1)
	void testFindAll() {
		// setup
		// execute
		List<Event> events = eventRepository.findAll();
		// verify
		int expectedSize = 2;
		assertEquals(expectedSize, events.size());
	}

	@Test
	@Order(2)
	void testFindById() {
		// setup
		int id = 1;
		// execute
		Event event = eventRepository.findById(id);
		// verify
		String expectedName = "JAVA SUMMIT 2020";
		assertEquals(expectedName, event.getName());
	}

	@Test
	@Order(3)
	void testSave() {
		// setup
		Event event = new Event("Javascript Future", "Tirana", LocalDateTime.of(2020, 12, 5, 18, 0),
				LocalDateTime.of(2020, 12, 5, 18, 0));
		// execute
		Event eventSaved = eventRepository.save(event);
		// verify
		assertEquals(event.getName(), eventSaved.getName());
	}

	@Test
	@Order(4)
	void testDeleteById() {
		// setup
		int id = 2;
		// execute
		eventRepository.deleteById(id);
		// verify
		List<Event> events = eventRepository.findAll();
		int expectedSize = 1;
		assertEquals(expectedSize, events.size());
	}

}
