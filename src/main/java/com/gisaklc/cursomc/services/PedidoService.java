package com.gisaklc.cursomc.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gisaklc.cursomc.domain.ItemPedido;
import com.gisaklc.cursomc.domain.PagamentoComBoleto;
import com.gisaklc.cursomc.domain.Pedido;
import com.gisaklc.cursomc.domain.enums.EstadoPagamento;
import com.gisaklc.cursomc.repositories.ItemPedidoRespository;
import com.gisaklc.cursomc.repositories.PagamentoRespository;
import com.gisaklc.cursomc.repositories.PedidoRepository;
import com.gisaklc.cursomc.repositories.ProdutoRepository;
import com.gisaklc.cursomc.services.exceptions.ObjectNotFound;

/** Ao chamar o sendEmail a Interface EmailService não sabe qual objeto instanciar
 * com isso foi criado um metodo que é anotado 
 * com @Bean que é um "componente" na classe TestConfig pra instanciar 
 * correspondente o objeto automaticamente   **/

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRespository pagamentoRepositoy;

	@Autowired
	private ProdutoRepository produtoRepositoy;

	@Autowired
	private ItemPedidoRespository itemPedidoRepositoy;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		Optional<Pedido> obj = pedidoRepository.findById(id);
		return obj.orElseThrow(
				() -> new ObjectNotFound("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
	public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.getPagamento().setEstadoPagamento(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		obj.setCliente(clienteService.find(obj.getCliente().getId()));

		PagamentoComBoleto pb = new PagamentoComBoleto();
		pb.setDataPagamento(new Date());
		pb.setDataVencimento(new Date());
		pb.setIdPagamento(null);

		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagamentoComBoleto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagamentoComBoleto, obj.getInstante());
		}

		obj = pedidoRepository.save(obj);

		pagamentoRepositoy.save(obj.getPagamento());

		for (ItemPedido itemPedido : obj.getItens()) {
			itemPedido.setDesconto(0);
			itemPedido.setProduto(produtoService.find(itemPedido.getProduto().getId()));
			// itemPedido.setProduto(produtoRepositoy.findById(itemPedido.getProduto().getId()).orElse(null));
			// recupera o produto e pega o preco do produto e copia para o preco do item
			itemPedido.setPreco(itemPedido.getProduto().getPreco());
			// seta o pedido no item
			itemPedido.setPedido(obj);

		}

		// salva os itens do pedido
		itemPedidoRepositoy.saveAll(obj.getItens());
		
		emailService.sendOrderEmailConfirmation(obj);
		
	//	System.out.println(obj);//chama o toString
		return obj;
	}

}
