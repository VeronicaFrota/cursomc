package com.veronicafrota.cursomc.services;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.veronicafrota.cursomc.domain.Cliente;
import com.veronicafrota.cursomc.repositories.ClienteRepository;
import com.veronicafrota.cursomc.services.exceptions.ObjectNotFoundException;

// To generate the randon password (the new pasword)
@Service
public class AuthService {

	@Autowired
	private ClienteRepository clienteRepository;

	@Autowired
	private BCryptPasswordEncoder pe;

	@Autowired
	private EmailService emailService;

	private Random rand = new Random();


	// Method that will send the new password to the client (user)
	public void sendNewPassword(String email) {
		Cliente cliente = clienteRepository.findByEmail(email);			// Find the Cliente through the email

		if(cliente == null) {											// If the cliente does not exist, then the email is not registered
			throw new ObjectNotFoundException("E-mail não encontrado");
		}

		// Create the new password for the cliente (user)
		String newPass = newPassword();									// Returns a new random password
		cliente.setSenha(pe.encode(newPass));							// Set the new password with the password that was generated

		clienteRepository.save(cliente);								// Para salvar este cliente no banco de dados

		emailService.sendNewPasswordEmail(cliente, newPass);			// Send the email to the client
	}


	// Generate of new random password (Password with 10 characters that can be saws or letters)
	private String newPassword() {
		char[] vet = new char[10];
		
		// For each value of i, you will receive a random value
		for(int i = 0; i < 10; i++) {
			vet[i] = randomChar();
		}
		return new String(vet);
	}


	// Generates a random character
	private char randomChar() {
		int opt = rand.nextInt(3);										// To generate integers until two

		if(opt == 0) {													// Generates a digit
			return (char) (rand.nextInt(10) + 48);						// 10 = Number between 0 and 10 / AND 48 = Code of 0 in the character table
		}
		else if(opt == 1) {												// Generates a uppercase letter
			return (char) (rand.nextInt(26) + 65); 						// 26 = Number between A and Z  / AND 65 = Code of 0 in the character table
		}
		else {															// Generates a lowercase letter
			return (char) (rand.nextInt(26) + 97);						// 26 = Number between a and z  / AND 97 = Code of 0 in the character table
		}

	}

}
