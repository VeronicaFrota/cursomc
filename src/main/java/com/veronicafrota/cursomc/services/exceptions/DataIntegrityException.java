package com.veronicafrota.cursomc.services.exceptions;

// Service Exception
public class DataIntegrityException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	// Custom exception handling.
	public DataIntegrityException(String msg) {
		super(msg);
	}
	
	public DataIntegrityException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
