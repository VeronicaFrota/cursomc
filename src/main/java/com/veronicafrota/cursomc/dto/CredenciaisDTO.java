package com.veronicafrota.cursomc.dto;

import java.io.Serializable;

// Class that will receive the DTO with the email and password
//Serializable -> To facilitate file saving and network traffic
public class CredenciaisDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private String email;
	private String senha;
	

	// Empty Constructor
	public CredenciaisDTO() {

	}
	
	//Constructor with data
	public CredenciaisDTO(String email, String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}

	// Getters ans Setters
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
