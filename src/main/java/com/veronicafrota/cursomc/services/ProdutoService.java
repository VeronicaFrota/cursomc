package com.veronicafrota.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.veronicafrota.cursomc.domain.Categoria;
import com.veronicafrota.cursomc.domain.Produto;
import com.veronicafrota.cursomc.repositories.CategoriaRepository;
import com.veronicafrota.cursomc.repositories.ProdutoRepository;
import com.veronicafrota.cursomc.services.exceptions.ObjectNotFoundException;

// For service access and get the information from the repository

@Service
public class ProdutoService {

	@Autowired							// Declares dependency on an object of type CategoryRepository
	private ProdutoRepository repo;

	@Autowired							// Declares dependency on an object of type CategoryRepository
	private CategoriaRepository categoriaRepository;

	// Operation able to search category by code.
	// To perform category search using id.
	public Produto find(Integer id) {
		
		Produto obj = repo.findOne(id);

		// Message to handle error of objects not found.
		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id
					+ ", tipo: " + Produto.class.getName());
		}
		return obj;
	}

	// Implements search by page
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		List<Categoria> categorias = categoriaRepository.findAll(ids); 			// List of categories installed from ID (Generates list of categories)

		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, categorias, pageRequest);
	}
	
}
