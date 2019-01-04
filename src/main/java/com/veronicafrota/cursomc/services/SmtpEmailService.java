package com.veronicafrota.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

// To send the real email
public class SmtpEmailService extends AbstractEmailService {

	// MailSender instantiates the object created in the application.properties (the settings for sending mail), to use the e-mail data
	@Autowired
	private MailSender mailSender;
	
	// To send the email
	@Autowired
	private JavaMailSender javaMailSender;

	// To display the message in the server log
	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);


	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email...");
		mailSender.send(msg);				// To send the real email
		LOG.info("Email enviado.");
	}


	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Enviando email...");
		javaMailSender.send(msg);				// To send the real email
		LOG.info("Email enviado.");
	}

}
