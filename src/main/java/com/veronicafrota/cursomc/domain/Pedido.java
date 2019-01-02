package com.veronicafrota.cursomc.domain;

import java.io.Serializable;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonFormat;


@Entity
public class Pedido implements Serializable {

	// Implement the Serializable interface, which says that its objects can be, converted to a sequence of bits
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")					// Date Format Mask
	private Date instante;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "pedido") 	// So that the pedido ID is the same as the pagamento, mapping to pedido
	private Pagamento pagamento;

	@ManyToOne
	@JoinColumn(name = "cliente_id") 							// foreign key
	private Cliente cliente;

	@ManyToOne
	@JoinColumn(name = "endereco_de_entrega_id") 				// foreign key
	private Endereco enderecoDeEntrega;

	// The requested class must know your ItemPedido, collection of ItemPedido
	// "SET" to ensure that there is no item repeated in the Pedido
	@OneToMany(mappedBy = "id.pedido")
	private Set<ItemPedido> itens = new HashSet<>();

	// Empty constructor.
	public Pedido() {

	}

	// Constructor with data
	public Pedido(Integer id, Date instante, Cliente cliente, Endereco enderecoDeEntrega) {
		super();
		this.id = id;
		this.instante = instante;
		this.cliente = cliente;
		this.enderecoDeEntrega = enderecoDeEntrega;
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
		Pedido other = (Pedido) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	// Method to return the order total (Get used to the Json could see)
	public double getValorTotal() {
		double soma = 0.0;
		//  For each ItemPedido (ip) in Item list (itens)
		for(ItemPedido ip : itens) {
			soma = soma + ip.getSubTotal();
		}
		return soma;
	}


	// To concatenate the text and display the message that will be sent by email
	@Override
	public String toString() {

		NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));		// Formats for real (R$)

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss"); 			// Format the date
		
		StringBuilder builder = new StringBuilder();
		builder.append("Pedido número: ");
		builder.append(getId());
		builder.append(", Instante: ");
		builder.append(sdf.format(getInstante()));
		builder.append(", Cliente: ");
		builder.append(getCliente().getNome());
		builder.append(", Situação do pagamento: ");
		builder.append(getPagamento().getEstado().getDescricao());
		builder.append("\n Detalhes \n");
		
		// ItensPedido
		for(ItemPedido ip : getItens()) {
			builder.append(ip.toString());
		}

		builder.append("Valor total: ");
		builder.append(nf.format(getValorTotal()));

		return builder.toString();
	}


	// Getters and Setters
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getInstante() {
		return instante;
	}

	public void setInstante(Date instante) {
		this.instante = instante;
	}

	public Pagamento getPagamento() {
		return pagamento;
	}

	public void setPagamento(Pagamento pagamento) {
		this.pagamento = pagamento;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Endereco getEnderecoDeEntrega() {
		return enderecoDeEntrega;
	}

	public void setEnderecoDeEntrega(Endereco enderecoDeEntrega) {
		this.enderecoDeEntrega = enderecoDeEntrega;
	}

	public Set<ItemPedido> getItens() {
		return itens;
	}

	public void setItens(Set<ItemPedido> itens) {
		this.itens = itens;
	}
}
