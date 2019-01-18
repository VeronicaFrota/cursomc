package com.veronicafrota.cursomc.security;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

// From a user a token is generated
@Component
public class JWTUtil {
	
	@Value("${jwt.secret}")						// To get the value that is in the application.properties
	private String secret;

	@Value("${jwt.expiration}")					// To get the value that is in the application.properties
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

	// Checks if the token is valid
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);						 // Stores token claims (User and expiration time)

		if(claims != null) {									 // Checks if the token is valid
			String username = claims.getSubject();				 // Get the username (return the user)
			Date expirationDate = claims.getExpiration(); 		 // Get the expiration date
			Date now = new Date(System.currentTimeMillis());	 // Current date to test if token is expired

			// Checks if the token is valid
			if(username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}

		return false;
	}

	// Obter o usuário do token
	public String getUsername(String token) {
		Claims claims = getClaims(token);						 // Stores token claims (User and expiration time)

		if(claims != null) {									 // Checks if the token is valid
			return claims.getSubject();				 			 // Get the username (return the user)
		}

		return null;
	}


	// Get the claims from a token
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();	// Recover claims from a toke
		}
		catch (Exception e) {
			return null;
		}
	}
	
}
