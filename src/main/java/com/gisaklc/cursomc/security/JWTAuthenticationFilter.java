package com.gisaklc.cursomc.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.gisaklc.cursomc.dto.CredenciaisDTO;
/**
 * É EXECUTADO ASSIM Q CHAMAR O LOGIN (PADRAO DO SPRING SECURITY)
 * FILTRO QUE INTERCEPTA A REQUISIÇÃO DE LOGIN
 * VERIFICA AS CREDENCIAIS E AUTENTICA OU NÃO 
 * O USUARIO GERANDO O TOKEN JWT EM CASO DE AUTENTICAÇÃO
 * TOKEN (CODIGO CONTENDO O USARIO E O TEMPO DE EXPIRAÇÃO"
 * **/
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	private AuthenticationManager authenticationManager; // classe do spring security

	private JWTUtil jwtUtil;

	
	/**AS CLASSES AuthenticationManager E JWTUtil
	 *  FORAM INJETADAS PELO CONSTRUTOR **/
	public JWTAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
		setAuthenticationFailureHandler(new JWTAuthenticationFailureHandler());
		this.authenticationManager = authenticationManager;
		this.jwtUtil = jwtUtil;
	}

	/**METODO Q TENTA AUTENTICAR 
	 * TUDO PADRÃO DO SPRING**/
	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {

		try {
			//PEGA OS DADOS QUE VIERAM NA REQUISICAO EMAIL E SENHA E CONVERTE PARA A CLASSE CREDENCIAISDTO
			CredenciaisDTO creds = new ObjectMapper().readValue(req.getInputStream(), CredenciaisDTO.class);

			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(creds.getEmail(),

					creds.getSenha(), new ArrayList<>());

			//ESSE METODO QUE VERIFICA SE OS USUARIO E SENHA SAO VALIDOS 
			//O FRAMEWORK FAZ ISSO COM BASE DO Q FOI IMPLEMENTADO NO USERDETAILS
			Authentication auth = authenticationManager.authenticate(authToken);
			return auth;//RETORNA BADCREDETIONS SE CASO NAO AUTENTICAR
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	/** RETORNA O TOKEN CASO HOUVER 
	 * SUCESSO NA AUTENTICACAO **/
	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {

		String username = ((UserSS) auth.getPrincipal()).getUsername();
		String token = jwtUtil.generateToken(username);
		res.addHeader("Authorization", "Bearer " + token);
	}

	private class JWTAuthenticationFailureHandler implements AuthenticationFailureHandler {

		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
				AuthenticationException exception) throws IOException, ServletException {
			response.setStatus(401);
			response.setContentType("application/json");
			response.getWriter().append(json());
		}

		private String json() {
			long date = new Date().getTime();
			return "{\"timestamp\": " + date + ", " + "\"status\": 401, " + "\"error\": \"Não autorizado\", "
					+ "\"message\": \"Email ou senha inválidos\", " + "\"path\": \"/login\"}";
		}
	}
}