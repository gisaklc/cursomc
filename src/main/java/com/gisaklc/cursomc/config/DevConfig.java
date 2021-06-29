package com.gisaklc.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.gisaklc.cursomc.services.DbService;

@Configuration
@Profile("dev")
public class DevConfig {

	@Autowired
	private DbService dbService;

	@Value(="${spring.jpa.hibernate.ddl-auto=create}")
	private String strategy;
	
	// metodo responsavel por instanciar o bd no profile test
	@Bean
	public boolean instantiateDatabase() throws ParseException {

		// popula a base de dados
		dbService.instantiateTestDatabase();

		return true;
	}
}
