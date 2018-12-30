package com.veronicafrota.cursomc.resource;

import java.net.URI;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.veronicafrota.cursomc.domain.Categoria;
import com.veronicafrota.cursomc.domain.Pedido;
import com.veronicafrota.cursomc.dto.CategoriaDTO;
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



	// Method that receives a category in Json format and inserts into the database
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody Pedido obj){	//@RequestBody = Converts Json to java object automatically, Answer Http that has no body
		obj = service.insert(obj);								// Calls the service that inserts the new category into the database
		
		// To provide the ID for the URI
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();		// Creates the URI
	}
	
	
}
