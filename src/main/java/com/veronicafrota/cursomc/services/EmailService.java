package com.veronicafrota.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.veronicafrota.cursomc.domain.Pedido;

// "Contract" to say what operations the e-mail service should offer 
public interface EmailService {

	// Operation to send order confirmation e-mail 
	void sendOrderConfirmationEmail(Pedido obj);
	
	// Operation to send e-mail  
	void sendEmail(SimpleMailMessage msg);

}
