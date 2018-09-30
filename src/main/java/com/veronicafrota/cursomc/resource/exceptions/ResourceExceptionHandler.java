package com.veronicafrota.cursomc.resource.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.veronicafrota.cursomc.resource.StandardError;
import com.veronicafrota.cursomc.services.exceptions.ObjectNotFoundException;

import ch.qos.logback.core.status.Status;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	// Message to handle error of objects not found.
	@ExceptionHandler(ObjectNotFoundException.class)
	public ResponseEntity<StandardError> objectNotFound(ObjectNotFoundException e, HttpServletRequest request) {
		StandardError err = new StandardError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
	}
	
}
