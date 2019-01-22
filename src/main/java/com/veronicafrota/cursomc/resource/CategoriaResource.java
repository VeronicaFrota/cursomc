package com.veronicafrota.cursomc.resource;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.veronicafrota.cursomc.domain.Categoria;
import com.veronicafrota.cursomc.dto.CategoriaDTO;
import com.veronicafrota.cursomc.services.CategoriaService;

// REST controller accesses the Service layer (Ex: private CategoriaService service;)
// Service layer accesses the data access layer (Repository)

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;												// To access the service layer

	/* Get the id that comes from the url
	 * ResponseEntity<?>: special type of spring that already stores (encapsulation) various information from an HTTP response to a REST service, <?> is equivalent to any type
	 * @PathVariable: For the spring know that the URL ID will be the variable ID
	 */
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Categoria> find(@PathVariable Integer id) {
		Categoria obj = service.find(id);
		return ResponseEntity.ok().body(obj);										// Informs if the answer is ok
	}



	// Method that receives a category in Json format and inserts into the database
 	@PreAuthorize("hasAnyRole('ADMIN')") 											// To indicate that, in this case, only ADMIN can do this action
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Void> insert(@Valid @RequestBody CategoriaDTO objDto){	//@RequestBody = Converts Json to java object automatically, Answer Http that has no body
		Categoria obj = service.fromDTO(objDto);									// Convert objDto to an object
		obj = service.insert(obj);													// Calls the service that inserts the new category into the database
		
		// To provide the ID for the URI
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
				.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();									// Creates the URI
	}



	// Method responsible for changing the data from the url
	@RequestMapping(value="/{id}", method=RequestMethod.PUT)						// To get the URL ID and update with PUT
	@PreAuthorize("hasAnyRole('ADMIN')") 											// To indicate that, in this case, only ADMIN can do this action
	// @RequestBody Categoria obj = Receives the object and parameters
	// @PathVariable Integer id   =  Receive URL parameters
	public ResponseEntity<Void> update(@RequestBody Categoria obj, @PathVariable Integer id) {
		obj.setId(id); 																// To ensure that it is really the Category
		obj = service.update(obj);
		return ResponseEntity.noContent().build();
	}



	// Method responsible for deleting the data.
	@RequestMapping(value="/{id}", method=RequestMethod.DELETE)						// To get the URL ID and update with PUT
	@PreAuthorize("hasAnyRole('ADMIN')") 											// To indicate that, in this case, only ADMIN can do this action
	public ResponseEntity<Void> delete(@PathVariable Integer id){
		service.delete(id);
		return ResponseEntity.noContent().build();  
	}



	// Method responsible to list of category
	// Search the list of bank categories and convert them to DTO
	@RequestMapping(method=RequestMethod.GET)										// Without the ID why you want to return all categories
	public ResponseEntity<List<CategoriaDTO>> findAll() {				
		List<Categoria> list = service.findAll();									// Return all categories
		List<CategoriaDTO> listDto = list.stream().map(obj -> new CategoriaDTO(obj)).collect(Collectors.toList()); // Converts the list to a DTO list;
		return ResponseEntity.ok().body(listDto);									// Informs if the answer is ok
	}



	// Listing method per page
	@RequestMapping(value="/page", method=RequestMethod.GET)						// To list per page
	public ResponseEntity<Page<CategoriaDTO>> findPage(								// Setting To list per page with the optional parameter (@RequestParam )
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {				
		
		Page<Categoria> list = service.findPage(page, linesPerPage, orderBy, direction);	// Return the categories
		Page<CategoriaDTO> listDto = list.map(obj -> new CategoriaDTO(obj)); 				// Converts the category page to a DTO list;
		return ResponseEntity.ok().body(listDto);											// Informs if the answer is ok
	}	
	
}






























