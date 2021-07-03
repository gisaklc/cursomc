package com.gisaklc.cursomc.services;

import javax.mail.internet.MimeMessage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

/**
 * MailSender instancia o objeto com todos os dados do email declarado no
 * properties.dev /*
 * 
 * @author gisak
 *
 */

public class SmtpEmailService extends AbstractEmailService {

	@Autowired
	private MailSender mailSender; // envia o email sem html

	@Autowired
	private JavaMailSender javaMailSender;

	private static final Logger LOG = LoggerFactory.getLogger(MockEmailService.class);

	@Override
	public void sendEmail(SimpleMailMessage msg) {
		LOG.info("Enviando email");
		mailSender.send(msg);// aqui envia o email sem html
		LOG.info("Email enviado");

	}

	@Override
	public void sendHtmlEmail(MimeMessage msg) {
		LOG.info("Enviando email");
		javaMailSender.send(msg);// envia o email COM html
		LOG.info("Email enviado");

	}

}
