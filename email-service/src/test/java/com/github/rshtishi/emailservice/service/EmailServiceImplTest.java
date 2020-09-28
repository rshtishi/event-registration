package com.github.rshtishi.emailservice.service;

import java.util.HashMap;
import java.util.Map;

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
		Mail mail = new Mail("mr.shtishi@mail.com", "rando.shtishi@gmail.com", "Simple Title", "Simple Content");
		// execute
		emailService.sendEmail(mail);
		// verify
	}
	
	@Test
	@Order(2)
	void testSendMailWithTemplate() throws Exception {
		//setup
		Mail mail = new Mail("mr.shtishi@mail.com", "rando.shtishi@gmail.com", "Confirmation from Event Reistration", "");
		String templateName = "event-registration-template.ftl";
		Map<String, Object> templateModel = new HashMap<>();
		templateModel.put("name", "Rando Shtishi");
		templateModel.put("event","Java Summit");
		templateModel.put("identificationNo","123456987");
		templateModel.put("signature", "Rando");
		//execute
		emailService.sendEmail(mail, templateName, templateModel);
		
		
	}

}
