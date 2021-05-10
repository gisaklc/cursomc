package com.gisaklc.cursomc.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gisaklc.cursomc.domain.Categoria;
import com.gisaklc.cursomc.domain.Produto;
import com.gisaklc.cursomc.repositories.CategoriaRepository;
import com.gisaklc.cursomc.repositories.ProdutoRepository;
import com.gisaklc.cursomc.services.exceptions.ObjectNotFound;

@Service
public class ProdutoService {

	@Autowired
	private ProdutoRepository repo;
	
	@Autowired
	private CategoriaRepository repoCategoria;
	
	public Produto find(Integer id) {
		Optional<Produto> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFound("Objeto não encontrado! Id: " + id + ", Tipo: " + Produto.class.getName()));
	}

	
	public Page<Produto> search(String nome, List<Integer> ids, Integer page, Integer linesForPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesForPage, Direction.valueOf(direction), orderBy) ;
		List<Categoria> lista = repoCategoria.findAllById(ids);
		return repo.findDistinctByNomeContainingAndCategoriasIn(nome, lista, pageRequest);
	}


	
	
}
