package com.veronicafrota.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Produto implements Serializable{

	// Implement the Serializable interface, which says that its objects can be converted to a sequence of bits
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Double preco;

	@JsonIgnore															// omit to list of categories
	@ManyToMany
	@JoinTable(name = "PRODUTO_CATEGORIA",								// Name of column
			   joinColumns = @JoinColumn(name = "produto_id"),			// Name of foreign key
			   inverseJoinColumns = @JoinColumn(name = "categoria_id")	// Name of foreign key
    )
	private List<Categoria> categorias = new ArrayList<>();				// association between product and category (diagram)

	// The requested class must know your ItemPedido, collection of ItemPedido
	// "SET" to ensure that there is no item repeated in the Produto
	@JsonIgnore															// Not to be serialized, not serializing Pedido and Produto
	@OneToMany(mappedBy = "id.produto")
	private Set<ItemPedido> itens = new HashSet<>();

	// Order list associated with Ordered item
	@JsonIgnore															// Not to be serialized, not serializing Pedido and Produto
	public List<Pedido> getPedidos() {
		
		List<Pedido> lista = new ArrayList<>();
		
		for(ItemPedido x: itens) {			// Scroll through the item list
			lista.add(x.getPedido());		// For each item, add the Pedido to the list
		}
		return lista;
	}
	
	// Empty constructor
	public Produto() {
		
	}

	// Constructor with data. 
	public Produto(Integer id, String nome, Double preco) {
		super();
		this.id = id;
		this.nome = nome;
		this.preco = preco;
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
		Produto other = (Produto) obj;
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
	public Double getPreco() {
		return preco;
	}
	public void setPreco(Double preco) {
		this.preco = preco;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}

}
