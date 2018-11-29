package com.veronicafrota.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veronicafrota.cursomc.domain.Pedido;
import com.veronicafrota.cursomc.repositories.PedidoRepository;
import com.veronicafrota.cursomc.services.exceptions.ObjectNotFoundException;

// For service access and get the information from the repository

@Service
public class PedidoService {

	@Autowired							// Declares dependency on an object of type CategoryRepository
	private PedidoRepository repo; 

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

}
