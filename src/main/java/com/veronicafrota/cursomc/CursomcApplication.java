package com.veronicafrota.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.veronicafrota.cursomc.domain.Categoria;
import com.veronicafrota.cursomc.domain.Cidade;
import com.veronicafrota.cursomc.domain.Cliente;
import com.veronicafrota.cursomc.domain.Endereco;
import com.veronicafrota.cursomc.domain.Estado;
import com.veronicafrota.cursomc.domain.Produto;
import com.veronicafrota.cursomc.domain.enums.TipoCliente;
import com.veronicafrota.cursomc.repositories.CategoriaRepository;
import com.veronicafrota.cursomc.repositories.CidadeRepository;
import com.veronicafrota.cursomc.repositories.ClienteRepository;
import com.veronicafrota.cursomc.repositories.EnderecoRepository;
import com.veronicafrota.cursomc.repositories.EstadoRepository;
import com.veronicafrota.cursomc.repositories.ProdutoRepository;

// CommandLineRunner: method that allows you to perform an action when the application starts.

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;		// To access the repository, by entering the data

	@Autowired
	private ProdutoRepository produtoRepository;			// To access the repository, by entering the data

	@Autowired
	private EstadoRepository estadoRepository;				// To access the repository, by entering the data

	@Autowired
	private CidadeRepository cidadeRepository;				// To access the repository, by entering the data

	@Autowired
	private ClienteRepository clienteRepository;			// To access the repository, by entering the data

	@Autowired
	private EnderecoRepository enderecoRepository;			// To access the repository, by entering the data
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);

	}

	// Instantiating objects by saving through the repository with CommandLineRunner.
	@Override
	public void run(String... args) throws Exception {

		// Category Instance.
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");

		// Product Instance.
		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 800.00);
		Produto p3 = new Produto(null, "Mouse", 80.00);

		// Association between the products and the categories, informing which category belongs to which product. 
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		// Association between the products and the categories, informing which product belongs to which category.
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));
		
		categoriaRepository.save(Arrays.asList(cat1, cat2));	// Saves the object in the database with repository.
		produtoRepository.save(Arrays.asList(p1, p2, p3));		// Saves the object in the database with repository.
		
		// State Instance.
		Estado est1 = new Estado(null, "Minas Gerais");
		Estado est2 = new Estado(null, "São Paulo");

		// City Instance, informing which city is the state.
		
		Cidade c1 = new Cidade(null, "Uberlândia", est1);
		Cidade c2 = new Cidade(null, "São Paulo", est2);
		Cidade c3 = new Cidade(null, "Campinas", est2);
		
		// Association between the states and the cities, informing which state belongs to which cities.
		est1.getCidades().addAll(Arrays.asList(c1));
		est2.getCidades().addAll(Arrays.asList(c2, c3));

		estadoRepository.save(Arrays.asList(est1, est2));	// Saves the object in the database with repository.
		cidadeRepository.save(Arrays.asList(c1, c2, c3));	// Saves the object in the database with repository.
		
		// Client Instance.
		Cliente cli1 = new Cliente(null, "Maria Silva", "maria@gmail.com", "12345678919", TipoCliente.PESSOAFISICA);
		cli1.getTelefones().addAll(Arrays.asList("32324898", "767556789"));
		
		// Address Instance.
		Endereco e1 = new Endereco(null, "Rua Flores", "300", "Apto 203", "Jardim", "09987800", cli1, c1);
		Endereco e2 = new Endereco(null, "Avenida Matos", "105", "Sala 800", "Centro", "07865899", cli1, c2);
		
		// Association between the client and the address, informing which client belongs to which address.
		cli1.getEnderecos().addAll(Arrays.asList(e1, e2));
		
		clienteRepository.save(Arrays.asList(cli1));		// Saves the object in the database with repository.
		enderecoRepository.save(Arrays.asList(e1, e2));		// Saves the object in the database with repository.
	}
}
