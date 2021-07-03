package com.gisaklc.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.gisaklc.cursomc.services.DbService;
import com.gisaklc.cursomc.services.EmailService;
import com.gisaklc.cursomc.services.SmtpEmailService;

/** 
 * o properties dev usa o bd do mysql o xamp
 * 
 * **/


@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DbService dbService;

	@Value("${spring.jpa.hibernate.ddl-auto}")// recupera o ddl-auto "create ou nono" do arquivo properties 
	private String strategy;
	
	// metodo responsavel por instanciar o bd no profile test
	@Bean
	public boolean instantiateDatabase() throws ParseException {

		if(!"create".equals(strategy)) {
			return false;
		}
		
		// popula a base de dados
		dbService.instantiateTestDatabase();

		return true;
	}
	
	@Bean // componente qnd anota com @bean
	public EmailService emailService() {
		return new SmtpEmailService(); // o spring procura o componente que deve instanciar automaticamente
	}
}
