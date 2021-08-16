package com.gisaklc.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// arry com os endpoint liberados para acesso
	private static final String[] PUBLIC_MATCHERS = 
		{
		  "/h2-console/**"
		};

	// arry com os endpoint liberados para acesso
	private static final String[] PUBLIC_MATCHERS_GET = 
		{ "/produtos/**",
		  "/categorias/**" 
		};
	
	
	@Autowired
	private Environment env;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();//liberar o acesso ao H2 de teste
		}

		http.cors().and().csrf().disable();// para ativar o bean abaixo
		http.authorizeRequests()
		.antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll() // permite os endpoint do vetor
		.antMatchers(PUBLIC_MATCHERS).permitAll()
				.anyRequest().authenticated(); // exige autenticacao para os restantes

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// nao cria sessão do usuario
	}

	// para permitir requisiçoes de multiplas fontes tem q liberar explicitamente
	// como um @bean
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}
}
