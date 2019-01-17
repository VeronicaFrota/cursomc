package com.veronicafrota.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.veronicafrota.cursomc.domain.Cliente;
import com.veronicafrota.cursomc.repositories.ClienteRepository;
import com.veronicafrota.cursomc.security.UserSS;

// Class to implements the UserDetailsService (Spring Security Agreement)
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private ClienteRepository repo;
	
	// Method that receives the user and returns the UserDetaisl (In UserSS)
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Cliente cli = repo.findByEmail(email);				// Find the client by email from the database (In ClienteRepository)

		if(cli == null) {									// If email is equal to null, then the client does not exist
			throw new UsernameNotFoundException(email);		// Inform email that does not exist
		}

		return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());	// User search by user name (and mail)
	}

}
