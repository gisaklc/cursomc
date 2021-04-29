package com.gisaklc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gisaklc.cursomc.domain.Categoria;
import com.gisaklc.cursomc.repositories.CategoriaRepository;
import com.gisaklc.cursomc.services.exceptions.DataIntegrityException;
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

	public Categoria save(Categoria categoria) {
		categoria.setId(null); // para nao ser update
		return repo.save(categoria);
	}

	public Categoria update(Categoria categoria) {
		find(categoria.getId());
		return repo.save(categoria);
	}

	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			throw new DataIntegrityException("Não é possivel deletar uma categoria que possui produtos");
		}
	}

	public List<Categoria> findAll() {
		List<Categoria> lista = repo.findAll();
		return lista;
	}

	public Page<Categoria> findPage(Integer page, Integer linesForPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesForPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);

	}

}
