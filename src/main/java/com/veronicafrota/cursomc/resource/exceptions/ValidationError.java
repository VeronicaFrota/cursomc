package com.veronicafrota.cursomc.resource.exceptions;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError {

	private static final long serialVersionUID = 1L;

	private List<FieldMessage> errors = new ArrayList<>();
	
	// Constructor
	public ValidationError(Integer status, String msg, Long timeStamp) {
		super(status, msg, timeStamp);

	}


	// Getters and Setters
	public List<FieldMessage> getError() {
		return errors;
	}

	public void setList(List<FieldMessage> list) {
		this.errors = list;
	}
	
	public void addError(String fieldName, String messagem) {
		errors.add(new FieldMessage(fieldName, messagem));
	}

}
