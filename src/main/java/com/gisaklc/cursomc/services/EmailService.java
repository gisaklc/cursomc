package com.gisaklc.cursomc.services;

import org.springframework.mail.SimpleMailMessage;

import com.gisaklc.cursomc.domain.Pedido;

public interface EmailService {

	void sendOrderEmailConfirmation(Pedido obj);

	void sendEmail(SimpleMailMessage msg);

}
