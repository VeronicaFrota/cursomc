package com.veronicafrota.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.veronicafrota.cursomc.domain.Cliente;
import com.veronicafrota.cursomc.domain.Pedido;

// "Contract" to say what operations the e-mail service should offer 
public interface EmailService {

	// Operation to send order confirmation e-mail 
	void sendOrderConfirmationEmail(Pedido obj);
	
	// Operation to send e-mail  
	void sendEmail(SimpleMailMessage msg);

	// Operation to send order confirmation e-mail (with HTML)
	void sendOrderConfirmationHtmlEmail(Pedido obj);

	// Operation to send e-mail (with HTML)
	void sendHtmlEmail(MimeMessage msg);

	// Operation to send new password tn the email
	void sendNewPasswordEmail(Cliente cliente, String newPass);
}
