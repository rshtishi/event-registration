package com.github.rshtishi.emailservice.service;

import com.github.rshtishi.emailservice.entity.Mail;

public interface EmailService {

	public void sendEmail(Mail mail);

	public void sendMimeEmail(Mail mail);
}
