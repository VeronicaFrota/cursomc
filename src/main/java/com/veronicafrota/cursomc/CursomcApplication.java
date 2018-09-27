package com.veronicafrota.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.veronicafrota.cursomc.domain.Categoria;
import com.veronicafrota.cursomc.repositories.CategoriaRepository;

// CommandLineRunner: method that allows you to perform an action when the application starts.

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	@Autowired
	private CategoriaRepository categoriaRepository;		// To access the repository, by entering the data
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);

	}

	// Instantiating objects by saving through the repository with CommandLineRunner.
	@Override
	public void run(String... args) throws Exception {
		
		Categoria cat1 = new Categoria(null, "Informática");
		Categoria cat2 = new Categoria(null, "Escritório");
		
		// Saves the object in the database with repository.
		categoriaRepository.save(Arrays.asList(cat1, cat2));
		
	}
}
