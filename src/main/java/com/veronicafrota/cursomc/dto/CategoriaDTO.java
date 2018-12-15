package com.veronicafrota.cursomc.dto;

import java.io.Serializable;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.veronicafrota.cursomc.domain.Categoria;

//Serializable -> To facilitate file saving and network traffic
public class CategoriaDTO implements Serializable{

	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message="Preenchimento obriatório")
	@Length(min=5, max=80, message="O tamanho deve ser entre 5 a 80 caracteres")
	private String nome;
	
	// Empty constructor
	public CategoriaDTO() {

	}

	// DTO instance from a category object
	public CategoriaDTO(Categoria obj) {
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
