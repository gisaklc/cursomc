package com.gisaklc.cursomc.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.gisaklc.cursomc.security.JWTAuthenticationFilter;
import com.gisaklc.cursomc.security.JWTAuthorizationFilter;
import com.gisaklc.cursomc.security.JWTUtil;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true) // dar acesso especifico
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	// arry com os endpoint liberados para acesso
	private static final String[] PUBLIC_MATCHERS = { "/h2-console/**" };

	// arry com os endpoint liberados para acesso
	private static final String[] PUBLIC_MATCHERS_GET = { "/produtos/**", "/categorias/**" };

	private static final String[] PUBLIC_MATCHERS_POST =
		{ "/clientes/",
		  "/auth/forgot/**" };

	@Autowired
	private Environment env;

	// injeta a instancia que foi implementada
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JWTUtil jwtUtil;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		if (Arrays.asList(env.getActiveProfiles()).contains("test")) {
			http.headers().frameOptions().disable();// liberar o acesso ao H2 de teste
		}

		http.csrf().disable();// para ativar o bean abaixo
		http.authorizeRequests().// permite os endpoint do
				antMatchers(HttpMethod.GET, PUBLIC_MATCHERS_GET).permitAll().// permite acesso com o cliente logado
				antMatchers(HttpMethod.POST, PUBLIC_MATCHERS_POST).permitAll().antMatchers(PUBLIC_MATCHERS).permitAll()
				.anyRequest().authenticated(); // exige autenticacao para os restantes
		http.addFilter(new JWTAuthenticationFilter(authenticationManager(), jwtUtil));
		http.addFilter(new JWTAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));

		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);// nao cria sessão do usuario
	}

	/**
	 * para permitir requisiçoes de multiplas fontes tem q liberar explicitamente
	 * 
	 * @return
	 */
	@Bean
	CorsConfigurationSource corsConfigurationSource() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", new CorsConfiguration().applyPermitDefaultValues());
		return source;
	}

	/**
	 * Algoritmo de encode p/ injetar em qualquer classe do sistema
	 * 
	 * @return
	 */
	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	/**
	 * o spring busca a implementação da interface que é a UserDetailsServiceImpl
	 * essa instancia indica qual a classe quem é capaz de buscar o usuario por
	 * email
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

}
