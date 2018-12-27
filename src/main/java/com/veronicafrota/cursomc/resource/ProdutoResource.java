package com.veronicafrota.cursomc.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.veronicafrota.cursomc.domain.Produto;
import com.veronicafrota.cursomc.dto.CategoriaDTO;
import com.veronicafrota.cursomc.dto.ProdutoDTO;
import com.veronicafrota.cursomc.resource.utils.URL;
import com.veronicafrota.cursomc.services.ProdutoService;

// REST controller accesses the Service layer (Ex: private ProdutoService service;)
// Service layer accesses the data access layer (Repository)

@RestController
@RequestMapping(value="/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;			// To access the service layer

	// Get the id that comes from the url
	// * ResponseEntity<?>: special type of spring that already stores (encapsulation) various information from an HTTP response to a REST service, <?> is equivalent to any type
	// * @PathVariable: For the spring know that the URL ID will be the variable ID
	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<Produto>find(@PathVariable Integer id) {
		
		Produto obj = service.find(id);
		return ResponseEntity.ok().body(obj);				// Informs if the answer is ok
	}


	// EndPoint to search
	// Listing method per page
	@RequestMapping(method=RequestMethod.GET)				// To list per page
	public ResponseEntity<Page<ProdutoDTO>> findPage(		// Setting To list per page with the optional parameter (@RequestParam )
			@RequestParam(value="nome", defaultValue="") String nome,
			@RequestParam(value="categorias", defaultValue="") String categorias,
			@RequestParam(value="page", defaultValue="0") Integer page, 
			@RequestParam(value="linesPerPage", defaultValue="24") Integer linesPerPage, 
			@RequestParam(value="orderBy", defaultValue="nome") String orderBy,
			@RequestParam(value="direction", defaultValue="ASC") String direction) {				
		
		String nomeDecoded = URL.decodeParam(nome);			 // Converts the element to integer, then plays in the integer list
		List<Integer> ids = URL.decodeIntList(categorias);	 // To convert the list to integer
		
		Page<Produto> list = service.search(nomeDecoded, ids, page, linesPerPage, orderBy, direction);	// Return the categories
		Page<ProdutoDTO> listDto = list.map(obj -> new ProdutoDTO(obj)); 	// Converts the category page to a DTO list;
		return ResponseEntity.ok().body(listDto);								// Informs if the answer is ok
	}
	
}
