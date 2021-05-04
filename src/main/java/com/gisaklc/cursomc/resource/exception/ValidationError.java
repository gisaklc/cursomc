package com.gisaklc.cursomc.resource.exception;

import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandarError {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private List<FieldMessage> listErrors = new ArrayList<>();

	public ValidationError(Integer status, String msg, Long timestamp) {
		super(status, msg, timestamp);
	}

	public List<FieldMessage> getListErrors() {
		return listErrors;
	}

	public void addError(String fieldName, String message) {
		listErrors.add(new FieldMessage(fieldName, message));

	}

}
