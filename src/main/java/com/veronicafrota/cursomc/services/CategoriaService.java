package com.veronicafrota.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.veronicafrota.cursomc.domain.Categoria;
import com.veronicafrota.cursomc.repositories.CategoriaRepository;
import com.veronicafrota.cursomc.services.exceptions.DataIntegrityException;
import com.veronicafrota.cursomc.services.exceptions.ObjectNotFoundException;

// For service access and get the information from the repository

@Service
public class CategoriaService {

	@Autowired							// Declares dependency on an object of type CategoryRepository
	private CategoriaRepository repo; 

	// Operation able to search category by code, To perform category search using id.
	public Categoria find(Integer id) {
		Categoria obj = repo.findOne(id);

		if(obj == null) {
			throw new ObjectNotFoundException("Objeto não encontrado! Id: " + id	// Message to handle error of objects not found.
					+ ", tipo: " + Categoria.class.getName());
		}
		return obj;
	}

	// Insert new category
	public Categoria insert(Categoria obj) {
		obj.setId(null);		// To confirm that it is a new object and is not an existing one
		return repo.save(obj);
	}
	
	// Update the Category
	// When null ID, inserts, When ID is not null it updates
	public Categoria update(Categoria obj) {
		find(obj.getId());			// Checks if ID exists
		return repo.save(obj);
	}

	// Delete the Category
	public void delete(Integer id) {
		find(id);					// Checks if ID exists
		try{
			repo.delete(id);		// Deletes by ID
		}catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel excluir uma categoria que possui produtos.");
		}
	}

}
