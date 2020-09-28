package com.github.rshtishi.emailservice.service;

import java.util.Map;

import com.github.rshtishi.emailservice.entity.Mail;

public interface EmailService {

	public void sendEmail(Mail mail);

	public void sendEmail(Mail mail,String template, Map<String, Object> templateModel) throws Exception;
}
