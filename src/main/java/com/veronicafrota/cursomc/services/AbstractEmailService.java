package com.veronicafrota.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.veronicafrota.cursomc.domain.Pedido;

public abstract class AbstractEmailService implements EmailService {

	// To get the sender of the email
	@Value("${default.sender}")
	private String sender;
	
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

}
