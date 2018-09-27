package com.veronicafrota.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veronicafrota.cursomc.domain.Categoria;
import com.veronicafrota.cursomc.repositories.CategoriaRepository;

// For service access and get the information from the repository

@Service
public class CategoriaService {

	@Autowired							// Declares dependency on an object of type CategoryRepository
	private CategoriaRepository repo; 

	// Operation able to search category by code.
	// To perform category search using id.
	public Categoria buscar(Integer id) {
		
		Categoria obj = repo.findOne(id);
		return obj;
	}

}
