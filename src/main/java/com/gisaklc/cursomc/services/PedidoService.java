package com.gisaklc.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisaklc.cursomc.domain.Pedido;
import com.gisaklc.cursomc.repositories.PedidoRepository;
import com.gisaklc.cursomc.services.exceptions.ObjectNotFound;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository repo;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFound("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}



}
