package com.gisaklc.cursomc.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gisaklc.cursomc.domain.Categoria;
import com.gisaklc.cursomc.services.CategoriaService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/categorias")
public class CategoriaResource {

	@Autowired
	private CategoriaService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Categoria categoria = service.find(id);
		return ResponseEntity.ok().body(categoria);

	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAll(@PathVariable Integer id) {
		List<Categoria> categorias = service.findAll();
		return ResponseEntity.ok().body(categorias);

	}

}
