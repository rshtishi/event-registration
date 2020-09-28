package com.github.rshtishi.emailservice.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.github.rshtishi.emailservice.entity.Mail;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmailServiceImplTest {

	@Autowired
	private EmailService emailService;

	@Test
	@Order(1)
	@Disabled
	void testSendMail() {
		// setup
		Mail mail = new Mail("mr.shtishi@mail.com", "rando.shtishi@gmail.com", "hope", "this worked");
		// execute
		emailService.sendEmail(mail);
		// verify
	}

}
