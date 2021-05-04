package com.gisaklc.cursomc.resource.exception;

public class FieldMessage {

	private String name;
	private String message;

	public FieldMessage() {
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public FieldMessage(String name, String message) {
		super();
		this.name = name;
		this.message = message;
	}

}
