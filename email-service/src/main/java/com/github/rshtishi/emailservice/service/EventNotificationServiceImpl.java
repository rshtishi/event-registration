package com.github.rshtishi.emailservice.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.github.rshtishi.emailservice.entity.EventSubscriber;
import com.github.rshtishi.emailservice.entity.Mail;

@Service
public class EventNotificationServiceImpl implements EventNotificationService {

	public static String TEMPLATE_FILE_NAME = "event-registration-template.ftl";
	private static String EVENT_ORGANIZER = "EVENT-REGISTRATION-APP";
	private static String SUBJECT="Event Registration Confirmation Email";
	@Value("${spring.mail.username}")
	private String FROM;

	@Autowired
	private EmailService emailService;

	@Override
	public void notifyEventSubscriberByEmail(EventSubscriber subscriber) throws Exception {
		Map<String, Object> templateModel = new HashMap<>();
		templateModel.put("name", subscriber.getName());
		templateModel.put("event", subscriber.getEventName());
		templateModel.put("identificationNo",subscriber.getRegistrationId());
		templateModel.put("signature", EVENT_ORGANIZER);
		Mail mail = new Mail(FROM, subscriber.getEmail(), SUBJECT, null);
		emailService.sendEmail(mail, TEMPLATE_FILE_NAME, templateModel);
	}

}
