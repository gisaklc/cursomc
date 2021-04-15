package com.gisaklc.cursomc.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gisaklc.cursomc.domain.Cliente;
import com.gisaklc.cursomc.services.ClienteService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/clientes")
public class ClienteResource {

	@Autowired
	private ClienteService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Cliente cliente = service.find(id);
		return ResponseEntity.ok().body(cliente);

	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<?> findAll(@PathVariable Integer id) {
		List<Cliente> clientes = service.findAll();
		return ResponseEntity.ok().body(clientes);

	}

}
