package com.veronicafrota.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.veronicafrota.cursomc.domain.Cliente;
import com.veronicafrota.cursomc.dto.ClienteDTO;
import com.veronicafrota.cursomc.repositories.ClienteRepository;
import com.veronicafrota.cursomc.resource.exceptions.FieldMessage;

// Creates the validation that will be used in the validation of CPF or CNPJ
public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> {

	// Get URL ID
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {

		// Empty list to insert errors in list
		List<FieldMessage> list = new ArrayList<>();


		// Compare the customer ID you have in the URI with the customer ID you want to change
		// Get the map of URI variables that are in the request
		@SuppressWarnings("unchecked")
		Map <String, String> map = (Map <String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);	
		Integer uriId = Integer.parseInt(map.get("id"));


		// Check if email is unique
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if(aux != null && !aux.getId().equals(uriId)) {			// If aux is different from null, it means that the email already exists
			list.add(new FieldMessage("email", "E-mail já existente"));
		}



		// Go through the list that I created (Field Message) and for each object that is in the list, adds a corresponding error in the list of errors of frameWork
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}

		return list.isEmpty();		// If the list is empty then there is no error, so the isValid method is true
									// If the list is not empty then there is error, so the isValid method is false
	}
}