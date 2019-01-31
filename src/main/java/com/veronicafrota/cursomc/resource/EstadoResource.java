package com.veronicafrota.cursomc.resource;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.veronicafrota.cursomc.domain.Cidade;
import com.veronicafrota.cursomc.domain.Estado;
import com.veronicafrota.cursomc.dto.CidadeDTO;
import com.veronicafrota.cursomc.dto.EstadoDTO;
import com.veronicafrota.cursomc.services.CidadeService;
import com.veronicafrota.cursomc.services.EstadoService;

@RestController
@RequestMapping(value="/estados")
public class EstadoResource {

	@Autowired
	private EstadoService service;

	@Autowired
	private CidadeService cidadeService;

	// Find by esttados
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<EstadoDTO>> findAll() {
		List<Estado> list = service.findAll();																	// Receive Estado list
		List<EstadoDTO> listDto = list.stream().map(obj -> new EstadoDTO(obj)).collect(Collectors.toList());	// Convert the list of Estado to estadoDto
		
		return ResponseEntity.ok().body(listDto);
	}


	// Find by cidades
	@RequestMapping(value="/{estadoId}/cidades", method=RequestMethod.GET)
	public ResponseEntity<List<CidadeDTO>> findCidades(@PathVariable Integer estadoId) {
		List<Cidade> list = cidadeService.findByEstado(estadoId);												// Receive Cidade list
		List<CidadeDTO> listDto = list.stream().map(obj -> new CidadeDTO(obj)).collect(Collectors.toList());	// Convert the list of Cidade to cidadeDTO
		
		return ResponseEntity.ok().body(listDto);
	}
}
