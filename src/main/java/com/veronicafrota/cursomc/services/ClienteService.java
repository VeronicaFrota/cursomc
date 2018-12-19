package com.veronicafrota.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.veronicafrota.cursomc.domain.Cliente;
import com.veronicafrota.cursomc.dto.ClienteDTO;
import com.veronicafrota.cursomc.repositories.ClienteRepository;
import com.veronicafrota.cursomc.services.exceptions.DataIntegrityException;
import com.veronicafrota.cursomc.services.exceptions.ObjectNotFoundException;

// For service access and get the information from the repository

@Service
public class ClienteService {

	@Autowired // Declares dependency on an object of type CategoryRepository
	private ClienteRepository repo;

	// Operation able to search client by code.
	// To perform client search using id.
	public Cliente find(Integer id) {
		Cliente obj = repo.findOne(id);

		// Message to handle error of objects not found.
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", tipo: " + Cliente.class.getName());
		}
		return obj;
	}



	// Insert new client
	public Cliente insert(Cliente obj) {
		obj.setId(null);		// To confirm that it is a new object and is not an existing one
		return repo.save(obj);
	}



	// Update the Client
	// When null ID, inserts, When ID is not null it updates
	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());			// Instance the client from the database, checks if ID exists
		updateData(newObj, obj);					// Updates the data based on what comes from the arguments
		return repo.save(newObj);					// Saves the new data
	}



	// Refresh newObj data with data that came from obj
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}



	// Delete the Category
	public void delete(Integer id) {
		find(id);					// Checks if ID exists
		try{
			repo.delete(id);		// Deletes by ID
		} catch (DataIntegrityViolationException e) {	// Post custom exception
			throw new DataIntegrityException("Não é possivel excluir por que há entidades relacionadas.");
		}
	}


	// Find all client
	public List<Cliente> findAll() {
		return repo.findAll();		// Return all client
	}
	


	// To return a client page
	// Integer page: Page number
	// Integer linesPerPage: lines per page
	// String orderBy: Sort Attribute
	// String direction: Direction for sorting
	public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = new PageRequest(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}



	// Convert objDto to an object
	public Cliente fromDTO(ClienteDTO objDto) {
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null);
	}
	
}

