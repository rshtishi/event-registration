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

import com.github.rshtishi.app.entity.Attendee;
import com.github.rshtishi.app.entity.Event;

@DataJpaTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class AttendeeRepositoryTest {

	@Autowired
	private AttendeeRepository attendeeRepository;

	@Test
	@Order(1)
	void testFindAll() {
		// setup
		// execute
		List<Attendee> attendees = attendeeRepository.findAll();
		// verify
		int expectedSize = 3;
		assertEquals(expectedSize, attendees.size());
	}

	@Test
	@Order(2)
	void testFindById() {
		// setup
		int id = 1;
		// execute
		Attendee attendee = attendeeRepository.findById(id);
		// verify
		String expectedFullName = "Rando Shtishi";
		assertEquals(expectedFullName, attendee.getFullName());
	}
	
	@Test
	@Order(3)
	void testSave() {
		//setup
		Event event = new Event("Kafka Summit", "Tirana", LocalDateTime.of(2020, 12, 5, 18, 0),
				LocalDateTime.of(2020, 12, 5, 18, 0));
		Attendee attendee = new Attendee("Roni Fagu", "rfagu@gmil.com", event);
		attendee.setEvent(event);
		//execute
		Attendee attendeeSaved = attendeeRepository.save(attendee);
		//verify
		assertEquals(attendee.getFullName(), attendeeSaved.getFullName());
	}

}
