package com.gisaklc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisaklc.cursomc.domain.Categoria;
import com.gisaklc.cursomc.repositories.CategoriaRepository;
import com.gisaklc.cursomc.services.exceptions.ObjectNotFound;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository repo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFound("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}

	public List<Categoria> findAll() {
		List<Categoria> lista = repo.findAll();
		return lista;
	}

}
