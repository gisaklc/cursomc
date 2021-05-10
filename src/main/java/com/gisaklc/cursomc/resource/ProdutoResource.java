package com.gisaklc.cursomc.resource;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gisaklc.cursomc.domain.Produto;
import com.gisaklc.cursomc.dto.ProdutoDTO;
import com.gisaklc.cursomc.resource.utils.URL;
import com.gisaklc.cursomc.services.ProdutoService;

import javassist.tools.rmi.ObjectNotFoundException;

@RestController
@RequestMapping(value = "/produtos")
public class ProdutoResource {

	@Autowired
	private ProdutoService service;

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Produto> find(@PathVariable Integer id) throws ObjectNotFoundException {
		Produto produto = service.find(id);
		return ResponseEntity.ok().body(produto);

	}
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<Page<ProdutoDTO>> findAll(
			@RequestParam(value = "nome", defaultValue = "0") String nome,
			@RequestParam(value = "categorias", defaultValue = "0") String categorias,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "linesForPage", defaultValue = "24") Integer linesForPage,
			@RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
			@RequestParam(value = "direction", defaultValue = "ASC") String direction) {

		String nomeDecode = URL.decodeParam(nome);// tira os espaços em branco da string 
		
		List<Integer> ids = URL.decodeIntList(categorias);
		
		Page<Produto> listProdutosPaged = service.search(nomeDecode, ids, page, linesForPage, orderBy, direction);

		//lista de produto converte para lista de ProdutoDTO
		Page<ProdutoDTO> listDtoPaged = listProdutosPaged.map(obj -> new ProdutoDTO(obj));
		
		return ResponseEntity.ok().body(listDtoPaged);

	}
}
