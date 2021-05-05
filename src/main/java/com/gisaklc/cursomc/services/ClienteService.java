package com.gisaklc.cursomc.services;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.gisaklc.cursomc.domain.Cidade;
import com.gisaklc.cursomc.domain.Cliente;
import com.gisaklc.cursomc.domain.Endereco;
import com.gisaklc.cursomc.domain.enums.TipoCliente;
import com.gisaklc.cursomc.dto.ClienteDTO;
import com.gisaklc.cursomc.dto.ClienteNewDTO;
import com.gisaklc.cursomc.repositories.CidadeRepository;
import com.gisaklc.cursomc.repositories.ClienteRepository;
import com.gisaklc.cursomc.repositories.EnderecoRepository;
import com.gisaklc.cursomc.services.exceptions.DataIntegrityException;
import com.gisaklc.cursomc.services.exceptions.ObjectNotFound;

@Service
public class ClienteService {

	@Autowired
	private ClienteRepository repo;

	@Autowired
	private CidadeRepository repoCidade;

	@Autowired
	private EnderecoRepository repoEndereco;

	public Cliente find(Integer id) {
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFound("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}

	@Transactional
	public Cliente insert(Cliente cliente) {
		cliente.setId(null); // para nao ser update
		cliente = repo.save(cliente);
		repoEndereco.saveAll(cliente.getEnderecos());// salva a lista de endereco do cliente
		return cliente;
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
			throw new DataIntegrityException("Não é possivel deletar um cliente porque há pedidos relacionados");
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

	public Cliente fromDTO(ClienteDTO obj) {
		return new Cliente(obj.getId(), obj.getNome(), obj.getEmail(), null, null);
	}

	public Cliente fromDTO(ClienteNewDTO obj) {
		// monta o cliente
		Cliente cli = new Cliente(null, obj.getNome(), obj.getEmail(), obj.getCpfOuCnpj(),
				TipoCliente.toEnum(obj.getTipoClienteEnum()));

		Optional<Cidade> cidade = repoCidade.findById(obj.getCidadeId());

		Endereco end = new Endereco(null, obj.getLogradouro(), obj.getNumero(), obj.getComplemento(), obj.getBairro(),
				obj.getCep(), cli, cidade.get());

		Endereco end2 = new Endereco(null, "Rua dos Anjos", "2", obj.getComplemento(), obj.getBairro(),
				obj.getCep(), cli, cidade.get());
		
		cli.getEnderecos().addAll(Arrays.asList(end, end2));

		cli.getTelefones().add(obj.getTelefone1());

		if (obj.getTelefone2() != null) {

			cli.getTelefones().add(obj.getTelefone2());
		}

		if (obj.getTelefone3() != null) {

			cli.getTelefones().add(obj.getTelefone3());
		}
		return cli;
	}
}
