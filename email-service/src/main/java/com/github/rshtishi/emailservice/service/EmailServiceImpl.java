package com.github.rshtishi.emailservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.github.rshtishi.emailservice.entity.Mail;


@Service
public class EmailServiceImpl implements EmailService {
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Override
	public void sendEmail(Mail mail) {
	       SimpleMailMessage message = new SimpleMailMessage(); 
	        message.setFrom(mail.getFrom());
	        message.setTo(mail.getTo()); 
	        message.setSubject(mail.getSubject()); 
	        message.setText(mail.getContent());
	        emailSender.send(message);
	}

}
