package com.github.rshtishi.emailservice.configuration;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@Configuration
public class MailConfiguration {

	@Value("${spring.mail.host}")
	private String host;
	@Value("${spring.mail.port}")
	private int port;
	@Value("${spring.mail.username}")
	private String username;
	@Value("${spring.mail.password}")
	private String password;
	@Value("${spring.mail.properties.mail.smtp.auth}")
	private boolean enableStartTls;

	@Value("${mail.transport.protocol}")
	private String smtp;
	@Value("${mail.smtp.auth}")
	private boolean mailAuth;
	@Value("${mail.debug}")
	private boolean mailDebug;

	@Bean
	public JavaMailSender javaMailSender() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(host);
		mailSender.setPort(port);
		mailSender.setUsername(username);
		mailSender.setPassword(password);

		Properties props = mailSender.getJavaMailProperties();
		props.put("mail.transport.protocol", smtp);
		props.put("mail.smtp.auth", mailAuth);
		props.put("mail.smtp.starttls.enable", enableStartTls);
		props.put("mail.debug", mailDebug);
		mailSender.setJavaMailProperties(props);

		return mailSender;
	}
	

}
