package com.veronicafrota.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// From a user a token is generated
@Component
public class JWTUtil {
	
	@Value("${jwt.secret}")			// To get the value that is in the application.properties
	private String secret;

	@Value("${jwt.expiration}")		// To get the value that is in the application.properties
	private Long expiration;

	// Class to generate the Token
	// Jwts.builder(): Generate the token
	public String generateToken(String username) {
		return Jwts.builder()
				.setSubject(username)
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())
				.compact();
	}
	
}
