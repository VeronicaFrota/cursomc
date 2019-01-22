package com.veronicafrota.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.veronicafrota.cursomc.security.UserSS;

// Class to be able to recover only himself (Get a user logged in)
public class UserService {

	// Returns the user logged on to the system (Return the UserSS (In the security package)
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		}
		catch (Exception e) {
			return null;
		}
	}
}
