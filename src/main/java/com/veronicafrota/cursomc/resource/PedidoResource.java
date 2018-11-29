package com.veronicafrota.cursomc.resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.veronicafrota.cursomc.domain.Pedido;
import com.veronicafrota.cursomc.services.PedidoService;

// REST controller accesses the Service layer (Ex: private PedidoService service;)
// Service layer accesses the data access layer (Repository)

@RestController
@RequestMapping(value="/pedidos")
public class PedidoResource {

	@Autowired
	private PedidoService service;			// To access the service layer

	// Get the id that comes from the url
	// * ResponseEntity<?>: special type of spring that already stores (encapsulation) various information from an HTTP response to a REST service, <?> is equivalent to any type
	// * @PathVariable: For the spring know that the URL ID will be the variable ID
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Pedido>find(@PathVariable Integer id) {
		
		Pedido obj = service.find(id);
		return ResponseEntity.ok().body(obj);			// Informs if the answer is ok
	}
	
}
