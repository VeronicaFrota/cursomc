package com.veronicafrota.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veronicafrota.cursomc.domain.ItemPedido;
import com.veronicafrota.cursomc.domain.PagamentoComBoleto;
import com.veronicafrota.cursomc.domain.Pedido;
import com.veronicafrota.cursomc.domain.enums.EstadoPagamento;
import com.veronicafrota.cursomc.repositories.ItemPedidoRepository;
import com.veronicafrota.cursomc.repositories.PagamentoRepository;
import com.veronicafrota.cursomc.repositories.PedidoRepository;
import com.veronicafrota.cursomc.repositories.ProdutoRepository;
import com.veronicafrota.cursomc.services.exceptions.ObjectNotFoundException;

// For service access and get the information from the repository

@Service
public class PedidoService {

	@Autowired							// Declares dependency on an object of type CategoryRepository
	private PedidoRepository repo; 

	@Autowired
	private BoletoService boletoService;
	
	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoRepository produtoRepository;

	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	// Operation able to search category by code.
	// To perform category search using id.
	public Pedido find(Integer id) {
		
		Pedido obj = repo.findOne(id);

		// Message to handle error of objects not found.
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
					+ ", tipo: " + Pedido.class.getName());
		}
		return obj;
	}



	// Insert new Pedido
	public Pedido insert(Pedido obj) {
		obj.setId(null);										// To confirm that it is a new object and is not an existing one
		obj.setInstante(new Date());							// Creates a new date with the current moment
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE); //  Sets the State of the payment, cartão or boleto
		obj.getPagamento().setPedido(obj); 						//  The Pagamento has to meet his request

		//  If the payment is of type PagamentoComBoleto, it shall produce a date for the expiration of the ticket 
		if(obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());	//  Fill in the payment due date, which will be a week after the moment of request 
		}
		
		obj = repo.save(obj);									// Saves the Pedido in the Bank
		pagamentoRepository.save(obj.getPagamento());			// Saves the Pagamento in the Banck
		
		// Adds 0 discount for ItensPedido 
		for(ItemPedido ip: obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setPreco(produtoRepository.findOne(ip.getProduto().getId()).getPreco());	//  Get the product ID to do a set in the price
			ip.setPedido(obj); // Associates the itemPedido with the Pedido that this by entering 
		}
		
		itemPedidoRepository.save(obj.getItens()); 				// Saves the ItemPedido in the Banck
		
		return obj;

	}

}
