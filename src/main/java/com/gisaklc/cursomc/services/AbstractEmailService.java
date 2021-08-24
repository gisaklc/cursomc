package com.gisaklc.cursomc.services;

import java.util.Date;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.gisaklc.cursomc.domain.Cliente;
import com.gisaklc.cursomc.domain.Pedido;

/**
 * A classe abstrata criada nao está implementando os dois métodos ao usar o
 * implements mas ao criar a primeira classe não abstrata que extender a classe
 * AbstractEmailService tem q implementar.
 * 
 * 
 * o protected para que seja acessado pelas subclasses e não pelos usuarios da
 * classe
 **/

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Autowired
	private TemplateEngine templateEngine;

	@Autowired
	private JavaMailSender javaMailSender;

	/** 
	 * 
	 * metodo chamado ao finalizar o pedido na classe pedidoService 
	 * **/
	@Override
	public void sendOrderEmailConfirmation(Pedido obj) {
		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}
	/** 
	 * 
	 * Prepara  envio de email sem html 
	 * **/
	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());// remetente
		sm.setFrom(sender);// destinatario
		sm.setSubject("Pedido confirmado Código : " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());// corpo do email o pedido
		return sm;
	}

	/** injetar o pedido no template tymleeaf **/
	protected String htmlFromTemplatePedido(Pedido obj) {
		Context c = new Context();
		c.setVariable("pedido", obj);// link para o template
		// processar o template e retornar o html em forma string com o TemplateEngine
		return templateEngine.process("email/confirmacaoPedido", c);

	}
	
	/** 
	 * 
	 * metodo chamado ao finalizar o pedido na classe pedidoService 
	 * **/
	@Override
	public void sendOrderConfirmationHtmlEmail(Pedido obj) {
		MimeMessage mime;
	
		try {
			mime = prepareMimeMessageFromPedido(obj);
			sendHtmlEmail(mime); // envia o email html
		} catch (MessagingException e) {
			sendOrderEmailConfirmation(obj); // se der algum erro envia o email plano sem html
		}

	}
	/** 
	 * 
	 * Prepara  envio de email html
	 * **/
	private MimeMessage prepareMimeMessageFromPedido(Pedido obj) throws MessagingException {
		MimeMessage mm = javaMailSender.createMimeMessage();
		MimeMessageHelper mmh = new MimeMessageHelper(mm, true);
		mmh.setTo(obj.getCliente().getEmail());// remetente
		mmh.setFrom(sender);// destinatario
		mmh.setSubject("Pedido confirmado Código : " + obj.getId());
		mmh.setSentDate(new Date(System.currentTimeMillis()));
		mmh.setText(htmlFromTemplatePedido(obj), true);// corpo do email o pedido
		return mm;
	}
	
	
	@Override
	public void sendNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = prepareNewPasswordEmail(cliente, newPass);
		sendEmail(sm);
	}
	//protected para a subclasse dar override
	protected SimpleMailMessage prepareNewPasswordEmail(Cliente cliente, String newPass) {
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(cliente.getEmail());
		sm.setFrom(sender);
		sm.setSubject("Solicitação de nova senha");
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText("Nova senha: " + newPass);
		return sm;
	}
	
}
