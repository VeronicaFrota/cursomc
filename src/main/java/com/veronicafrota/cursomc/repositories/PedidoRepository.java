package com.veronicafrota.cursomc.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.veronicafrota.cursomc.domain.Cliente;
import com.veronicafrota.cursomc.domain.Pedido;

// JpaRepository<Categoria, Integer>: special kind of spring able to access data based on a type that is passed, 
// this type is the object (category) and the type of attribute value of the identified object (in this case, integer(ID))

//Informs that it is a repository.
@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Integer> {

	@Transactional(readOnly = true)
	Page<Pedido> findByCliente(Cliente cliente, Pageable pageRequest);			// Find Pedidos Per Cliente;

}
