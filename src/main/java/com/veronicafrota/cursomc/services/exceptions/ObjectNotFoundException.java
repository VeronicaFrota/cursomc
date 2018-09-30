package com.veronicafrota.cursomc.services.exceptions;

public class ObjectNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	// Custom exception handling.
	public ObjectNotFoundException(String msg) {
		super(msg);
	}
	
	public ObjectNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
