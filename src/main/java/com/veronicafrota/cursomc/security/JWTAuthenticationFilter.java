package com.veronicafrota.cursomc.security;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.veronicafrota.cursomc.dto.CredenciaisDTO;

//  Intercepta a requisição (Ver se o usuario e senha estão corretos), executa alguma coisa antes e se der certo, ele devolve a execução
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter{

	private AuthenticationManager authenticationManager;

	private JWTUtil jwtUtil;

	// Constructor with data
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}	

	
	// Try to authenticate
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, 
											  HttpServletResponse res) throws AuthenticationException {
		try {
			// Try to get the email and password that came in the requisition
			CredenciaisDTO creds = new ObjectMapper()
					.readValue(req.getInputStream(), CredenciaisDTO.class);
			
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(), creds.getSenha(), new ArrayList<>());
	
			// authenticationManager.authenticate(authToken): Verifies that the user and password are valid (based on UserDetaisl, UserDetaisService)
			Authentication auth = authenticationManager.authenticate(authToken);
	
			// Informs whether authentication was successful or not
			return auth;		
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	// If Authentication Happens Successfully (Generate a token and append to the request response)
	@Override
	protected void successfulAuthentication(HttpServletRequest req, 
			  							   HttpServletResponse res,
			  							   FilterChain chain,
			  							   Authentication auth) throws IOException, ServletException {

		String username = ((UserSS) auth.getPrincipal()).getUsername();		// Return the user (email)
		String token =  jwtUtil.generateToken(username);					// Add the username (email) to the token
		res.addHeader("Authorization", "Bearer " + token);					// Add the token in the header
		
	
	
	}

}
