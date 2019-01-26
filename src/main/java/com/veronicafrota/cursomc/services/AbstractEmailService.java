package com.veronicafrota.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.veronicafrota.cursomc.domain.Cliente;
import com.veronicafrota.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	// To get the sender of the email
	@Value("${default.sender}")
	private String sender;


	// Process the template
	@Autowired
	private TemplateEngine templateEngine;


	// To get the Pedido and generate the object of type mimeMessage
	@Autowired
	private JavaMailSender javaMailSender;


	// To send the email
	@Override
	public void sendOrderConfirmationEmail(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}


	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(obj.getCliente().getEmail());								// To whom the message is sent, that is, to the client that made the request 
		sm.setFrom(sender);													// Email sender
		sm.setSubject("Pedido confirmado! Código: " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis())); 				// Email date
		sm.setText(obj.toString()); 										// Email body

		return sm;															// Call the sendEmail in sendOrderConfirmationEmail and return
	}


	// Responsible for returning the HTML filled with the data of an order, from the Thymeleaf template, that is, 
	// it takes the data of an Pedido and adds it in Thymeleaf (Fill in the template with the data)
	protected String htmlFromTemplatePedido(Pedido obj) {

		Context context = new Context();

		// context.setVariable("pedido", ...): "pedido" corresponding to the request called in Thymeleaf in confirmationRequest.html
		// context.setVariable(..., obj):  Obj is the value that will be passed to the "request" in thymelleaf
		context.setVariable("pedido", obj);

		// Process the template
		// templateEngine.process("email/confirmacaoPedido", ...): Template path
		return templateEngine.process("email/confirmacaoPedido", context);
	}
	

	@Override
	// Prepare to send the email
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {

		// Try to send email with HTML
		try {
			MimeMessage mm = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mm);
		}
		// If you can not send email without HTML
		catch(MessagingException e) {
			sendOrderConfirmationEmail(obj);
		}
	}


	// To get the Pedido and generate the object of type mimeMessage
	protected MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		
		MimeMessage mimeMessage = javaMailSender.createMimeMessage();		// To create the mimeMessage
		MimeMessageHelper mmh = new MimeMessageHelper(mimeMessage, true);	// To assign values to the message
		mmh.setTo(obj.getCliente().getEmail()); 							// To whom the message is sent, that is, to the client that made the request
		mmh.setFrom(sender);												// Email sender
		mmh.setSubject("Pedido confirmado! Código: " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis())); 				// Email date
		mmh.setText(htmlFromTemplatePedido(obj), true);						// Email body

		return mimeMessage;
	}


	// To send new password in the email
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(sm);
	}


	// Sending a new password
	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		
		sm.setTo(cliente.getEmail());										// To whom the message is sent, that is, to the client that made the request 
		sm.setFrom(sender);													// Email sender
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis())); 				// Email date
		sm.setText("Nova senha: " + newPass);								// Email body

		return sm;															// Call the sendEmail in sendOrderConfirmationEmail and return	
	}
	

}
