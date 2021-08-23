package com.gisaklc.cursomc.security;

import java.util.Date;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
/** 
 * @Component para ser injetada em outras classes
 * Essa classe q gera o 
 * token a partir do usuario passado
 * 
 * **/

@Component
public class JWTUtil {
	
	/**  importa o valor do arq 
	 * aplication.propriert**/
	@Value("${jwt.secret}") 
	private String secret;

	@Value("${jwt.expiration}")
	private Long expiration;
	
	//gera um token a partir do usuario
	public String generateToken(String username) {
		return Jwts.builder()//gera o token
				.setSubject(username)//usuario
				.setExpiration(new Date(System.currentTimeMillis() + expiration))//date baseado no tempo de expiracao
				.signWith(SignatureAlgorithm.HS512, secret.getBytes())//como vai assinar o token
				.compact();
	}
	
	public boolean tokenValido(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			String username = claims.getSubject();
			Date expirationDate = claims.getExpiration();
			Date now = new Date(System.currentTimeMillis());
			if (username != null && expirationDate != null && now.before(expirationDate)) {
				return true;
			}
		}
		return false;
	}

	public String getUsername(String token) {
		Claims claims = getClaims(token);
		if (claims != null) {
			return claims.getSubject();
		}
		return null;
	}
	
	private Claims getClaims(String token) {
		try {
			return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
		}
		catch (Exception e) {
			return null;
		}
	}
}