package com.gisaklc.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.SimpleMailMessage;

/**
 * 
 * o Static no log para nao precisar ficar instanciando...
 * 
 * 
 **/

public class MockEmailService extends AbstractEmailService {

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Simulando envio de email");
		LOG.info(msg.toString());
		LOG.info("Email enviado");

	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Simulando envio de email html");
		LOG.info(msg.toString());
		LOG.info("Email enviado");
	}

}
