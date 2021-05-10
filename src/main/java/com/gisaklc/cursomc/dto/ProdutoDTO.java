package com.gisaklc.cursomc.dto;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.gisaklc.cursomc.domain.Produto;

public class ProdutoDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer id;

	@NotEmpty(message = "Nome do produto é obrigatório")
	@Size(min = 5, max = 80, message = "O nome deve conter entre  5 e 80 caracteres ")
	private String nome;

	@NotEmpty(message = "O preço do produto é obrigatório")
	private double preco;

	public ProdutoDTO() {
	}

	public ProdutoDTO(Produto p) {
		id = p.getId();
		nome = p.getNome();
		preco = p.getPreco();
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

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

}
