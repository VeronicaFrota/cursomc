package com.veronicafrota.cursomc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.veronicafrota.cursomc.domain.Cidade;;

// JpaRepository<Categoria, Integer>: special kind of spring able to access data based on a type that is passed, 
// this type is the object (category) and the type of attribute value of the identified object (in this case, integer(ID))

@Repository // Informs that it is a repository.
public interface CidadeRepository extends JpaRepository<Cidade, Integer> {

}
