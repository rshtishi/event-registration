package com.github.rshtishi.emailservice.service;

import java.util.Map;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.github.rshtishi.emailservice.entity.Mail;

import freemarker.template.Template;

@Service
public class EmailServiceImpl implements EmailService {

	@Autowired
	private JavaMailSender emailSender;
	@Autowired
	private FreeMarkerConfigurer freemarkerConfigurer;

	@Override
	public void sendEmail(Mail mail) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setFrom(mail.getFrom());
		message.setTo(mail.getTo());
		message.setSubject(mail.getSubject());
		message.setText(mail.getContent());
		emailSender.send(message);
	}

	@Override
	public void sendEmail(Mail mail, String templateFilename, Map<String, Object> templateModel) throws Exception {
		Template template = freemarkerConfigurer.createConfiguration().getTemplate(templateFilename);
		String htmlBody = FreeMarkerTemplateUtils.processTemplateIntoString(template, templateModel);
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
		helper.setFrom(mail.getFrom());
		helper.setTo(mail.getTo());
		helper.setSubject(mail.getSubject());
		helper.setText(htmlBody, true);
		emailSender.send(message);
	}

}
