package com.veronicafrota.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

//Serializable -> To facilitate file saving and network traffic
public class EmailDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	@NotEmpty(message="Preenchimento obrigatório")
	@Email(message="E-mail inválido")
	private String email;

	// Empty constructor
	public EmailDTO() {

	}

	// Constructor with data
	public EmailDTO(String email) {
		super();
		this.email = email;
	}

	// Getter and Setter
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
