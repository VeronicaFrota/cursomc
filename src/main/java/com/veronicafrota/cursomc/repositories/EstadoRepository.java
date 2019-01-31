package com.veronicafrota.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.veronicafrota.cursomc.domain.Estado;

// JpaRepository<Categoria, Integer>: special kind of spring able to access data based on a type that is passed, 
// this type is the object (category) and the type of attribute value of the identified object (in this case, integer(ID))

@Repository // Informs that it is a repository.
public interface EstadoRepository extends JpaRepository<Estado, Integer> {

	@Transactional(readOnly = true)
	public List<Estado> findAllByOrderByNome();
	
}
