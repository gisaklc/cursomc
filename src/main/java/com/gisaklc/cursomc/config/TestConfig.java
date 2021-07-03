package com.gisaklc.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.gisaklc.cursomc.services.DbService;
import com.gisaklc.cursomc.services.EmailService;
import com.gisaklc.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	private DbService dbService;

	// metodo responsavel por instanciar o bd no profile test
	@Bean
	public boolean instantiateDatabase() throws ParseException {

		// popula a base de dados
		dbService.instantiateTestDatabase();

		return true;
	}

	@Bean // componente qnd anota com @bean
	public EmailService emailService() {
		return new MockEmailService(); // o spring procura o componente que deve instanciar automaticamente
	}
}
