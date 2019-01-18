package com.veronicafrota.cursomc.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

	private JWTUtil jwtUtil;

	private UserDetailsService userDetailsService;				// To verify user by email

	// To parse the token and see if it is valid(Filter to authorization)
	public JWTAuthorizationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsService userDetaisService) {
		super(authenticationManager);
		this.jwtUtil = jwtUtil;
		this.userDetailsService = userDetaisService;
	}


	// Method that intercepts the request and verifies if it is authorized (Do something before the requisition continues)
	@Override
	protected void doFilterInternal(HttpServletRequest request, 
									HttpServletResponse response,
									FilterChain chain) throws IOException, ServletException {

		String header = request.getHeader("Authorization");		// Receives as argument (handle) the name of the header (TOKEN)

		// To release access
		if(header != null && header.startsWith("Bearer ")) {	// Check if the header exists And start with "Bearer"
			UsernamePasswordAuthenticationToken auth = getAuthentication(header.substring(7)); // Get the token, removing the 7 letters of "Bearer"
			
			if(auth != null) {									// Release access
				SecurityContextHolder.getContext().setAuthentication(auth);
			}
		}

		chain.doFilter(request, response);						// The normal execution of the request continues
	}


	// Generate the object from the token
	private UsernamePasswordAuthenticationToken getAuthentication(String token) {
		if(jwtUtil.tokenValido(token)) {					// If the token is valid, it is authorized
			String username = jwtUtil.getUsername(token);	// Get the user name inside the token
			UserDetails user = userDetailsService.loadUserByUsername(username);
			return new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities()); 
		}

		return null;
	}

}
