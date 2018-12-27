package com.veronicafrota.cursomc.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.veronicafrota.cursomc.domain.Categoria;
import com.veronicafrota.cursomc.domain.Produto;

// JpaRepository<Categoria, Integer>: special kind of spring able to access data based on a type that is passed, 
// this type is the object (category) and the type of attribute value of the identified object (in this case, integer(ID))

@Repository // Informs that it is a repository.
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

	// @Query: Used to be created a query, that is, this query is not standard used in the framework, so a query was created
	// To add custom JPQL
	// @Query("SELECT DISTINCT obj FROM Produto obj INNER JOIN obj.categorias cat WHERE obj.nome LIKE %:nome% AND cat IN :categoria")
	// @Param: Take the value of the variable and play in the place of like(nome), as well as take the value of the variable and play in the place of category
	//Page<Produto> search(@Param("nome") String nome, @Param("categoria") List<Categoria> categorias, Pageable pageRequest);	// To bring the paging search from the bank
	
	// Filtering by category and product pagination
	@Transactional(readOnly = true)
	Page<Produto> findDistinctByNomeContainingAndCategoriasIn (String nome, List<Categoria> categorias, Pageable pageRequest);	// To bring the paging search from the bank
}
