package com.gisaklc.cursomc;

import java.text.SimpleDateFormat;
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
import com.gisaklc.cursomc.domain.ItemPedido;
import com.gisaklc.cursomc.domain.Pagamento;
import com.gisaklc.cursomc.domain.PagamentoComBoleto;
import com.gisaklc.cursomc.domain.PagamentoComCartao;
import com.gisaklc.cursomc.domain.Pedido;
import com.gisaklc.cursomc.domain.Produto;
import com.gisaklc.cursomc.domain.enums.EstadoPagamento;
import com.gisaklc.cursomc.domain.enums.TipoCliente;
import com.gisaklc.cursomc.repositories.CategoriaRepository;
import com.gisaklc.cursomc.repositories.CidadeRepository;
import com.gisaklc.cursomc.repositories.ClienteRepository;
import com.gisaklc.cursomc.repositories.EnderecoRepository;
import com.gisaklc.cursomc.repositories.EstadoRepository;
import com.gisaklc.cursomc.repositories.ItemPedidoRespository;
import com.gisaklc.cursomc.repositories.PagamentoRespository;
import com.gisaklc.cursomc.repositories.PedidoRepository;
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
	
	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private PagamentoRespository pagamentoRepository;
	
	@Autowired
	private ItemPedidoRespository itemPedidoRepository;
	
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
	
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy hh:mm");
		
		Pedido pedido1 = new Pedido(null, sdf.parse("15/04/2021 00:00"), cli1, end1);
		Pedido pedido2 = new Pedido(null, sdf.parse("20/03/2020 00:00"), cli1, end2);
		
		Pagamento pagto2 = new PagamentoComCartao(null, EstadoPagamento.QUITADO, pedido1, 3);
		
		Pagamento pagto1 = new PagamentoComBoleto(null, EstadoPagamento.PENDENTE,
				pedido2, sdf.parse("20/04/2021 00:00"),  null);
		
		pedido1.setPagamento(pagto1);
		
		pedido2.setPagamento(pagto2);
		
		cli1.getPedidos().addAll(Arrays.asList(pedido1, pedido2));
		
		pedidoRepository.saveAll(Arrays.asList(pedido1, pedido2));
		
		pagamentoRepository.saveAll(Arrays.asList(pagto1, pagto2));
		
		
		ItemPedido item1 = new ItemPedido(pedido1, p1, 0.00, 1, 300.00);
		ItemPedido item2 = new ItemPedido(pedido1, p2, 0.00, 2, 80.00);
		ItemPedido item3 = new ItemPedido(pedido2, p3, 0.00, 1, 30.00);
		
		pedido1.getItensPedidos().addAll(Arrays.asList(item1, item2)); //os pedidos conhecem os seus itens
		pedido2.getItensPedidos().addAll(Arrays.asList(item3));
		
		p1.getItensPedidos().addAll(Arrays.asList(item1));// os produtos conhecem os seus itens
		p2.getItensPedidos().addAll(Arrays.asList(item2));
		p3.getItensPedidos().addAll(Arrays.asList(item3));
		
		itemPedidoRepository.saveAll(Arrays.asList(item1, item2, item3));
		
	}

}
