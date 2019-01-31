package com.veronicafrota.cursomc.dto;

import java.io.Serializable;

import com.veronicafrota.cursomc.domain.Cidade;

// Serializable -> To facilitate file saving and network traffic
public class CidadeDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String nome;

	// Empty constructor
	public CidadeDTO() {

	}

	// Constructor with data
	public CidadeDTO(Cidade obj) {
		id = obj.getId();
		nome = obj.getNome();
	}


	// Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
