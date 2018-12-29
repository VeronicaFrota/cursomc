package com.veronicafrota.cursomc.domain;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemPedido implements Serializable {

	// Implement the Serializable interface, which says that its objects can be converted to a sequence of bits
	private static final long serialVersionUID = 1L;

	@JsonIgnore										// Not to be serialized, not serializing Pedido and Produto 
	@EmbeddedId										// It was added @Embeddable to say that the class is a sub type, because it is called as a type in ItemPedido
	private ItemPedidoPK id = new ItemPedidoPK();	// The ID is of the ItemPedidPK type, since it contains the reference between the two classes, Order and Product

	private Double desconto;
	private Integer quantidade;
	private Double preco;

	
	// Empty constructor
	public ItemPedido() {

	}

	// Constructor with data	
	public ItemPedido(Pedido pedido, Produto produto, Double desconto, Integer quantidade, Double preco) {
		super();
		id.setPedido(pedido);				// Assign Pedido to ID in ItemPedidoPK
		id.setProduto(produto);				// Assign Produto to ID in ItemPedidoPK
		this.desconto = desconto;
		this.quantidade = quantidade;
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
		ItemPedido other = (ItemPedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	// To calculate the total value of the items in the request (Get used to the Json could see)
	public double getSubTotal() {
		return (preco - desconto) * quantidade;
	}
	
	// Getters and setters
	@JsonIgnore						// Not to be serialized, not serializing Pedido and Produto
	public Pedido getPedido() {
		return id.getPedido();
	}

	public Produto getProduto() {
		return id.getProduto();
	}
	
	public Double getDesconto() {
		return desconto;
	}

	public void setDesconto(Double desconto) {
		this.desconto = desconto;
	}

	public Integer getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Integer quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;
	}
}
