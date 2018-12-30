package com.veronicafrota.cursomc.domain;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;
import com.veronicafrota.cursomc.domain.enums.EstadoPagamento;

@Entity
@JsonTypeName("pagamentoComCartao")			// Identifies the class, linked with the @type added in Pagamento
public class PagamentoComCartao extends Pagamento {

	// Implement the Serializable interface, which says that its objects can be converted to a sequence of bits
	private static final long serialVersionUID = 1L;

	private Integer numeroDeParcelas;

	// Empty constructor
	public PagamentoComCartao() {
	
	}

	// Constructor with data
	public PagamentoComCartao(Integer id, EstadoPagamento estado, Pedido pedido, Integer numeroDeParcelas) {
		super(id, estado, pedido);
		this.numeroDeParcelas = numeroDeParcelas;
	}
	
	// Getters and Setters
	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
}
