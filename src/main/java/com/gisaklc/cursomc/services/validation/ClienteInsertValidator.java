package com.gisaklc.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.gisaklc.cursomc.domain.Cliente;
import com.gisaklc.cursomc.domain.enums.TipoCliente;
import com.gisaklc.cursomc.dto.ClienteNewDTO;
import com.gisaklc.cursomc.repositories.ClienteRepository;
import com.gisaklc.cursomc.resource.exception.FieldMessage;
import com.gisaklc.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {

	@Autowired
	private ClienteRepository repo;

	@Override
	public void initialize(ClienteInsert ann) {
	}

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();

		if (objDto.getTipoClienteEnum().equals(TipoCliente.PESSOAFISICA.getCod())
				&& !BR.isValidCpf(objDto.getCpfOuCnpj())) {

			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		if (objDto.getTipoClienteEnum().equals(TipoCliente.PESSOAJURIDICA.getCod())
				&& !BR.isValidCnpj(objDto.getCpfOuCnpj())) {

			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}
		Cliente cli = repo.findByEmail(objDto.getEmail());

		if (cli != null) {

			list.add(new FieldMessage("email", "Já existe o email cadastro"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
