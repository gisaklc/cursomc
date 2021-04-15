package com.gisaklc.cursomc;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.gisaklc.cursomc.domain.Categoria;
import com.gisaklc.cursomc.domain.Cidade;
import com.gisaklc.cursomc.domain.Cliente;
import com.gisaklc.cursomc.domain.Endereco;
import com.gisaklc.cursomc.domain.Estado;
import com.gisaklc.cursomc.domain.Produto;
import com.gisaklc.cursomc.domain.enums.TipoCliente;
import com.gisaklc.cursomc.repositories.CategoriaRepository;
import com.gisaklc.cursomc.repositories.CidadeRepository;
import com.gisaklc.cursomc.repositories.ClienteRepository;
import com.gisaklc.cursomc.repositories.EnderecoRepository;
import com.gisaklc.cursomc.repositories.EstadoRepository;
import com.gisaklc.cursomc.repositories.ProdutoRepository;

@SpringBootApplication
public class CursomcApplication implements CommandLineRunner {

	@Autowired
	private CategoriaRepository categoriaRepository;

	@Autowired
	private ProdutoRepository produtoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(CursomcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		Categoria cat1 = new Categoria(null, "Informatica");
		Categoria cat2 = new Categoria(null, "Escritorio");

		Produto p1 = new Produto(null, "Computador", 2000.00);
		Produto p2 = new Produto(null, "Impressora", 1000.00);
		Produto p3 = new Produto(null, "Mouse", 50.00);

		// associação muitos para muitos
		// adiciona na lista os produtos da categoria
		cat1.getProdutos().addAll(Arrays.asList(p1, p2, p3));
		cat2.getProdutos().addAll(Arrays.asList(p2));

		// associação adiciona na lista as categorias de cada produto
		p1.getCategorias().addAll(Arrays.asList(cat1));
		p2.getCategorias().addAll(Arrays.asList(cat1, cat2));
		p3.getCategorias().addAll(Arrays.asList(cat1));

		// salva no banco

		categoriaRepository.saveAll(Arrays.asList(cat1, cat2));
		produtoRepository.saveAll(Arrays.asList(p1, p2, p3));

		Estado e1 = new Estado(null, "Minas Gerais");
		Estado e2 = new Estado(null, "Rio de Janeiro");

		Cidade c1 = new Cidade(null, "Petropolis");
		Cidade c2 = new Cidade(null, "Brumadinho");
		Cidade c3 = new Cidade(null, "Guapimirim");

		e1.getCidades().addAll(Arrays.asList(c2));
		e2.getCidades().addAll(Arrays.asList(c1, c3));
		
		c1.setEstado(e2);
		c2.setEstado(e1);
		c3.setEstado(e2);
		
		estadoRepository.saveAll(Arrays.asList(e1, e2));
		cidadeRepository.saveAll(Arrays.asList(c1, c2, c3));
		
		
		Cliente cli1 = new Cliente(null, "Maria Silva", "12119663726", "mariasilva@hotmail.com", TipoCliente.PESSOAFISICA );
		
		cli1.getTelefones().addAll(Arrays.asList("2197265825", "982353652"));//adiciona os telefones do cliente
		
		
		Endereco end1 = new Endereco(null, "Rua sei la", "2", "beira linha", "Jardim A", "209402020", cli1, c1);
		Endereco end2 = new Endereco(null, "Rua Toma la da cá", "2", "Buraco do boi", "A sorte é sua", "20202232", cli1, c3);
		//esse cliente tem dois enderecos
		cli1.getEnderecos().addAll(Arrays.asList(end1, end2));// adiciona os enderecos do cliente
		
		clienteRepository.saveAll(Arrays.asList(cli1));
		enderecoRepository.saveAll(Arrays.asList(end1, end2));
		
		
	}

}
