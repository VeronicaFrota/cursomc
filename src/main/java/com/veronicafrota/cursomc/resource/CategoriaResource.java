package com.veronicafrota.cursomc.resource;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.veronicafrota.cursomc.domain.Categoria;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {

	@RequestMapping(method=RequestMethod.GET)
	// Method to list category.
	public List<Categoria> listar() {

		// Instantiating the categories.
		Categoria cat1 = new Categoria(1, "Informática");
		Categoria cat2 = new Categoria(2, "Escritório");
		
		// list of categories.
		List<Categoria> lista = new ArrayList<>();
		lista.add(cat1); // Add categories in list.
		lista.add(cat2); // Add categories in list.

		// Returns the list of categories.
		return lista;
	}
	
}
