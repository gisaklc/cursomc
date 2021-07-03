package com.gisaklc.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.springframework.mail.SimpleMailMessage;

import com.gisaklc.cursomc.domain.Pedido;

public interface EmailService {

	/** 
	 * Email com texto plano
	 *  **/
	void sendOrderEmailConfirmation(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
	/** 
	 * Email HTML
	 *  **/
	void sendOrderConfirmationHtmlEmail(Pedido obj);
	
	void sendHtmlEmail(MimeMessage msg);
	
	
	
}
