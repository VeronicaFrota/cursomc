package com.veronicafrota.cursomc.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.veronicafrota.cursomc.domain.Estado;
import com.veronicafrota.cursomc.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository repo;

	// To do Estado search by name
	public List<Estado> findAll() {
		return repo.findAllByOrderByNome();
	}

}
