package com.veronicafrota.cursomc.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.veronicafrota.cursomc.domain.Cliente;
import com.veronicafrota.cursomc.domain.Cliente;
import com.veronicafrota.cursomc.dto.ClienteDTO;
import com.veronicafrota.cursomc.dto.ClienteDTO;
import com.veronicafrota.cursomc.services.ClienteService;

// REST controller accesses the Service layer (Ex: private ClienteService service;)
// Service layer accesses the data access layer (Repository)

@RestController
@RequestMapping(value="/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;			// To access the service layer

	// Get the id that comes from the url
	// * ResponseEntity<?>: special type of spring that already stores (encapsulation) various information from an HTTP response to a REST service, <?> is equivalent to any type
	// * @PathVariable: For the spring know that the URL ID will be the variable ID
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Cliente>find(@PathVariable Integer id) {
		
		Cliente obj = service.find(id);
		return ResponseEntity.ok().body(obj);			// Informs if the answer is ok
	}
	
	// Method that receives a client in Json format and inserts into the database
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody ClienteDTO objDto){	//@RequestBody = Converts Json to java object automatically, Answer Http that has no body
		Cliente obj = service.fromDTO(objDto);							// Convert objDto to an object
		obj = service.insert(obj);											// Calls the service that inserts the new cliente into the database
		
		// To provide the ID for the URI
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();		// Creates the URI
		
	}
	
	// Method responsible for changing the data from the url
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)		// To get the URL ID and update with PUT
	// @RequestBody Cliente obj = Receives the object and parameters
	// @PathVariable Integer id   =  Receive URL parameters
	public ResponseEntity<Void> update(@RequestBody Cliente obj, @PathVariable Integer id) {
		obj.setId(id); 												// To ensure that it is really the Category
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}

	// Method responsible for deleting the data.
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)		// To get the URL ID and update with PUT
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();  
	}

	// Method responsible to list of cliente
	// Search the list of bank categories and convert them to DTO
	@RequestMapping(method=RequestMethod.GET)						// Without the ID why you want to return all categories
	public ResponseEntity<List<ClienteDTO>> findAll() {				
		List<Cliente> list = service.findAll();					// Return all categories
		List<ClienteDTO> listDto = list.stream().map(obj -> new ClienteDTO(obj)).collect(Collectors.toList()); // Converts the list to a DTO list;
		return ResponseEntity.ok().body(listDto);					// Informs if the answer is ok
	}


	// Listing method per page
	@RequestMapping(value="/page", method=RequestMethod.GET)		// To list per page
	public ResponseEntity<Page<ClienteDTO>> findPage(				// Setting To list per page with the optional parameter (@RequestParam )
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {				
		
		Page<Cliente> list = service.findPage(page, linesPerPage, orderBy, direction);	// Return the categories
		Page<ClienteDTO> listDto = list.map(obj -> new ClienteDTO(obj)); 	// Converts the client page to a DTO list;
		return ResponseEntity.ok().body(listDto);								// Informs if the answer is ok
	}	
	
}

