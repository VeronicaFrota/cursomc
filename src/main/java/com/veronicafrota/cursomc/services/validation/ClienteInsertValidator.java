package com.veronicafrota.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.veronicafrota.cursomc.domain.enums.TipoCliente;
import com.veronicafrota.cursomc.dto.ClienteNewDTO;
import com.veronicafrota.cursomc.resource.exceptions.FieldMessage;
import com.veronicafrota.cursomc.services.validation.util.BR;

// Creates the validation that will be used in the validation of CPF or CNPJ
public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {

		// Empty list to insert errors in list
		List<FieldMessage> list = new ArrayList<>();

		// If the type is equal to physical persons and if validation of the CPF is not valid
		if(objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
		    list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));	// Adds an error to the list of errors
		}


		// If the type is equal to physical persons and if validation of the CNPF is not valid
		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));	// Adds an error to the list of errors
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