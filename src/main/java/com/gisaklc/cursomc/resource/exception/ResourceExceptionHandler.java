package com.gisaklc.cursomc.resource.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.gisaklc.cursomc.services.exceptions.DataIntegrityException;
import com.gisaklc.cursomc.services.exceptions.ObjectNotFound;

@ControllerAdvice
public class ResourceExceptionHandler {

	@ExceptionHandler(ObjectNotFound.class)
	public ResponseEntity<StandarError> objectNotFound(ObjectNotFound e, HttpServletRequest request) {
		StandarError err = new StandarError(HttpStatus.NOT_FOUND.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);

	}

	@ExceptionHandler(DataIntegrityException.class)
	public ResponseEntity<StandarError> DateIntegration(DataIntegrityException e, HttpServletRequest request) {
		StandarError err = new StandarError(HttpStatus.BAD_REQUEST.value(), e.getMessage(), System.currentTimeMillis());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<StandarError> MethodArgumentNotValid(MethodArgumentNotValidException e,
			HttpServletRequest request) {
		ValidationError err = new ValidationError(HttpStatus.BAD_REQUEST.value(), "Erro de Validação",
				System.currentTimeMillis());

		for (FieldError field : e.getBindingResult().getFieldErrors()) {
			
			err.addError(field.getField(), field.getDefaultMessage());
		}

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);

	}

}