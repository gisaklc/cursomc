package com.gisaklc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gisaklc.cursomc.domain.Cliente;
import com.gisaklc.cursomc.dto.ClienteDTO;
import com.gisaklc.cursomc.repositories.ClienteRepository;
import com.gisaklc.cursomc.services.exceptions.DataIntegrityException;
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

	public Cliente update(Cliente obj) {
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}

	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel deletar um cliente porque há entidades relacionadas");
		}
	}

	public List<Cliente> findAll() {
		List<Cliente> lista = repo.findAll();
		return lista;
	}

	// paginacao
	public Page<Cliente> findPage(Integer page, Integer linesForPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesForPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);

	}

	public Cliente fromClienteDTO(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null);
	}

}
