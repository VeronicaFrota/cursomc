package com.veronicafrota.cursomc.domain;

import java.io.Serializable;

public class Categoria implements Serializable {

	// Implement the Serializable interface, which says that its objects can be converted to a sequence of bits
	private static final long serialVersionUID = 1L;

	// Parameters
	private Integer id;
	private String nome;

	// Empty constructor
	public Categoria() {

	}

	// Constructor with data
	public Categoria(Integer id, String nome) {
		super();
		this.id = id;
		this.nome = nome;
	}
	
	// HashCode Equals = To compare objects by value
	// HashCode = generates a numerical code for each object
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	// Equals = method that compares two objects
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Categoria other = (Categoria) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
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
