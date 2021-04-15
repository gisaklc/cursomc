package com.gisaklc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisaklc.cursomc.domain.Cliente;
import com.gisaklc.cursomc.repositories.ClienteRepository;
import com.gisaklc.cursomc.services.exceptions.ObjectNotFound;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFound("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	public List<Cliente> findAll() {
		List<Cliente> lista = repo.findAll();
		return lista;
	}

}
