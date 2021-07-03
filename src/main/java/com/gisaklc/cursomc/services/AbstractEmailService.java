package com.gisaklc.cursomc.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import com.gisaklc.cursomc.domain.Pedido;

/**
 * A classe abstrata criada nao está implementando os dois métodos ao usar o
 * implements mas ao criar a classe filha tem q implementar.
 * 
 * 
 * o protected para que seja acessado pelas subclasses e não pelos usuarios da
 * classe
 **/

public abstract class AbstractEmailService implements EmailService {

	@Value("${default.sender}")
	private String sender;

	@Override
	public void sendOrderEmailConfirmation(Pedido obj) {

		SimpleMailMessage sm = prepareSimpleMailMessageFromPedido(obj);
		sendEmail(sm);
	}

	protected SimpleMailMessage prepareSimpleMailMessageFromPedido(Pedido obj) {
		// TODO Auto-generated method stub
		SimpleMailMessage sm = new SimpleMailMessage();
		sm.setTo(obj.getCliente().getEmail());// remetente
		sm.setFrom(sender);// destinatario
		sm.setSubject("Pedido confirmado Código : " + obj.getId());
		sm.setSentDate(new Date(System.currentTimeMillis()));
		sm.setText(obj.toString());// corpo do email o pedido
		return sm;
	}

}
