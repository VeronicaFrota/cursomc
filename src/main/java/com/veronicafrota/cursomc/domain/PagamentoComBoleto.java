package com.veronicafrota.cursomc.domain;

import java.util.Date;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonTypeName;
import com.veronicafrota.cursomc.domain.enums.EstadoPagamento;

@Entity
@JsonTypeName("pagamentoComBoleto")			// Identifies the class, linked with the @type added in Pagamento 
public class PagamentoComBoleto extends Pagamento {
	
	// Implement the Serializable interface, which says that its objects can be converted to a sequence of bits
	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern = "dd/MM/yyyy")				// Date Format Mask
	private Date dataVencimento;

	@JsonFormat(pattern = "dd/MM/yyyy")				// Date Format Mask
	private Date dataPagamento;
	
	// Empty constructor
	public PagamentoComBoleto() {
		
	}

	// Constructor with data
	public PagamentoComBoleto(Integer id, EstadoPagamento estado, Pedido pedido, Date dataVencimento, Date dataPagamento) {
		super(id, estado, pedido);
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
		
	}

	// Getters and Setters
	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
}
