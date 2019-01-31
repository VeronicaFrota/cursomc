package com.veronicafrota.cursomc.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.veronicafrota.cursomc.domain.Cidade;;

// JpaRepository<Categoria, Integer>: special kind of spring able to access data based on a type that is passed, 
// this type is the object (category) and the type of attribute value of the identified object (in this case, integer(ID))

@Repository // Informs that it is a repository.
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

	@Transactional(readOnly = true)
	// :estadoId: nickname for estado_id, to be used, must add @Param("estadoId")
	@Query("SELECT obj FROM Cidade  obj WHERE obj.estado.id = :estadoId ORDER BY obj.nome")
	public List<Cidade> findCidades(@Param("estadoId") Integer estado_id);
	
}
