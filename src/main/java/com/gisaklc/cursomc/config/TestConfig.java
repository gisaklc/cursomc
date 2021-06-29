package com.gisaklc.cursomc.config;

import java.text.ParseException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.gisaklc.cursomc.services.DbService;

@Configuration
@Profile("test")
public class TestConfig {

	@Autowired
	DbService dbService;

	// metodo responsavel por instanciar o bd no profile test
	@Bean
	public boolean instantiateDatabase() throws ParseException {

		// popula a base de dados
		dbService.instantiateTestDatabase();

		return true;
	}
}
