package com.veronicafrota.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.veronicafrota.cursomc.domain.Cidade;
import com.veronicafrota.cursomc.domain.Cliente;
import com.veronicafrota.cursomc.domain.Endereco;
import com.veronicafrota.cursomc.domain.enums.Perfil;
import com.veronicafrota.cursomc.domain.enums.TipoCliente;
import com.veronicafrota.cursomc.dto.ClienteDTO;
import com.veronicafrota.cursomc.dto.ClienteNewDTO;
import com.veronicafrota.cursomc.repositories.CidadeRepository;
import com.veronicafrota.cursomc.repositories.ClienteRepository;
import com.veronicafrota.cursomc.repositories.EnderecoRepository;
import com.veronicafrota.cursomc.security.UserSS;
import com.veronicafrota.cursomc.services.exceptions.AuthorizationException;
import com.veronicafrota.cursomc.services.exceptions.DataIntegrityException;
import com.veronicafrota.cursomc.services.exceptions.ObjectNotFoundException;

// For service access and get the information from the repository

@Service
public class ClienteService {

	@Autowired
	private BCryptPasswordEncoder pe;				// Inject bean (added in the SecurityConfig class) to encrypt the user's password

	@Autowired
	private ClienteRepository repo;					// Declares dependency on an object of type CategoryRepository

	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;


	// Operation able to search client by code (To perform client search using id.)
	public Cliente find(Integer id) {
		
		// Get the user logged in
		UserSS user = UserService.authenticated();
		if(user == null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}

		
		Cliente obj = repo.findOne(id);

		// Message to handle error of objects not found.
		if (obj == null) {
			throw new ObjectNotFoundException(
					"Objeto não encontrado! Id: " + id + ", tipo: " + Cliente.class.getName());
		}
		return obj;
	}



	// Insert new client
	@Transactional										// To ensure it will save both client and address in the same transaction
	public Cliente insert(Cliente obj) {
		obj.setId(null);								// To confirm that it is a new object and is not an existing one
		obj = repo.save(obj);
		enderecoRepository.save(obj.getEnderecos());	// To save the address
		return obj;
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
		find(id);										// Checks if ID exists
		try{
			repo.delete(id);							// Deletes by ID
		} catch (DataIntegrityViolationException e) {	// Post custom exception
			throw new DataIntegrityException("Não é possivel excluir por que há pedidos relacionados.");
		}
	}


	// Find all client
	public List<Cliente> findAll() {
		return repo.findAll();							// Return all client
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
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}



	// Convert objDto to an object to DTO
	public Cliente fromDTO(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Cidade cid = new Cidade(objDto.getCidadeId(), null, null);
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid);

		cli.getEnderecos().add(end);					// Add addresses to client

		cli.getTelefones().add(objDto.getTelefone1());	// Add telephones to client
		
		//Add multiple phones to the client
		if(objDto.getTelefone2() != null) {
			cli.getTelefones().add(objDto.getTelefone2());
		}
		if(objDto.getTelefone3() != null) {
			cli.getTelefones().add(objDto.getTelefone3());
		}
		
		return cli;
	}
	
	
}

