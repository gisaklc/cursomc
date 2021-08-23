package com.gisaklc.cursomc.services;

import org.springframework.security.core.context.SecurityContextHolder;

import com.gisaklc.cursomc.security.UserSS;

public class UserService {

	//static para acessar sem intanciar
	public static UserSS authenticated() {
		try {
			return (UserSS) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		} catch (Exception e) {//pega qualquer exceção retorna null
			return null;
		}
	}
}
