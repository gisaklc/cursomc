package com.gisaklc.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.gisaklc.cursomc.domain.Cliente;
import com.gisaklc.cursomc.services.validation.ClienteUpdate;

@ClienteUpdate
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer id;
	
	@NotEmpty(message = "Nome é obrigatório")
	@Size(min = 5, max = 120, message = "O nome deve conter entre  5 e 120 caracteres ")
	private String nome;
	
	@NotEmpty(message = "Email é obrigatório")
	@Email(message = "Email inválido")
	private String email;

	
	public ClienteDTO() {
	}

	public ClienteDTO(Cliente cl) {
		id = cl.getId();
		nome = cl.getNome();
		email = cl.getEmail();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	
	
}
