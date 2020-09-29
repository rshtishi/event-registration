package com.github.rshtishi.emailservice.service;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.github.rshtishi.emailservice.entity.EventSubscriber;
import com.github.rshtishi.emailservice.entity.Mail;


class EventNotificationServiceImplTest {
	
	@InjectMocks
	private EventNotificationServiceImpl eventNotificationService;
	@Mock
	private EmailService emailService;
	
	@BeforeEach
	public void setup() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testNotifyEventSubscriberByEmail() throws Exception {
		//setup
		doNothing().when(emailService).sendEmail(Mockito.any(), Mockito.anyString(), Mockito.any());
		EventSubscriber subscriber = new EventSubscriber("Rando Shtishi", "rshtishi@mail.com", "Java Summit", "2323435454");
		//execute
		eventNotificationService.notifyEventSubscriberByEmail(subscriber);
		//verify
		verify(emailService,times(1)).sendEmail(Mockito.any());
	}

}
