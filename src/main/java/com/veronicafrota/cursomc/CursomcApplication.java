package com.veronicafrota.cursomc;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// CommandLineRunner: method that allows you to perform an action when the application starts.

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner{

	public static void main(String[] args) {

		SpringApplication.run(CursomcApplication.class, args);

	}

	// Instantiating objects by saving through the repository with CommandLineRunner.
	@Override
	public void run(String... args) throws Exception {

	}

}
