package com.veronicafrota.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.veronicafrota.cursomc.domain.enums.Perfil;
import com.veronicafrota.cursomc.domain.enums.TipoCliente;

@Entity
public class Cliente implements Serializable{

	// Implement the Serializable interface, which says that its objects can be converted to a sequence of bits
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String nome;

	@Column(unique=true)										// To ensure email is unique
	private String email;

	private String cpfOuCnpj;
	private Integer tipo;

	@JsonIgnore													// To not show the password in Json
	private String senha;

	@JsonIgnore													// For cyclic Json serialization, to use @JsonBackReference in the address class so that it can not serialize the client class
	@OneToMany(mappedBy = "cliente")							// To refer to who was mapped, in this case, pedido
	private List<Pedido> pedidos = new ArrayList<>();

	@OneToMany(mappedBy = "cliente", cascade = CascadeType.ALL)	// Cascade all deletes all client addresses that exclude and do not access requests
	private List<Endereco> enderecos = new ArrayList<>();		// The client has an address list
	
	// Collection of strings associated with the client, phone being represented by a set of strings,
	// Set is a set that does not accept repetition
	@ElementCollection											// maps as weak entity
	@CollectionTable(name = "telefone")							// Auxiliary table that stores your phone data
	private Set<String> telefones = new HashSet<>();

	// For customer profiles
	// Collection of strings associated with the client, phone being represented by a set of strings
	// Set is a set that does not accept repetition
	@ElementCollection(fetch = FetchType.EAGER)					// EAGER: ensures that whenever a customer search is carried out, also bring your profiles
	@CollectionTable(name = "PERFIS")
	private Set<Integer> perfis = new HashSet<>();
	
	// Empty constructor
	public Cliente() {
		addPerfil(Perfil.CLIENTE);								// For default always add the customer profile to customer
	}
	
	// Constructor with data
	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipo, String senha) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipo = (tipo == null) ? null: tipo.getCod();		// To get the client-type cod
		this.senha = senha;
		addPerfil(Perfil.CLIENTE);								// For default always add the customer profile to customer
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
		Cliente other = (Cliente) obj;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public TipoCliente getTipo() {
		return TipoCliente.toEnum(tipo);		// verification method called in the enum
	}

	public void setTipo(TipoCliente tipo) {
		this.tipo = tipo.getCod();
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}

	// Scrolls the collection by converting all to the enumerated type profile
	public Set<Perfil> getPerfis() {
		return perfis.stream().map(x -> Perfil.toEnum(x)).collect(Collectors.toSet());
	}

	// Add profile
	public void addPerfil(Perfil perfil) {
		perfis.add(perfil.getCod());
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	
}
