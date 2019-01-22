package com.veronicafrota.cursomc.services.exceptions;

// Service Exception
public class AuthorizationException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	// Custom exception handling.
	public AuthorizationException(String msg) {
		super(msg);
	}
	
	public AuthorizationException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
