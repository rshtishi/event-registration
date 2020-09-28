package com.github.rshtishi.emailservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.github.rshtishi.emailservice.entity.Mail;


@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Override
	public void sendEmail(Mail mail) {
		// TODO Auto-generated method stub

	}

}
