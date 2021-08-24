package com.gisaklc.cursomc.resource;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gisaklc.cursomc.security.JWTUtil;
import com.gisaklc.cursomc.security.UserSS;
import com.gisaklc.cursomc.services.AuthService;
import com.gisaklc.cursomc.services.UserService;

/**REFRESH DO TOKEN PRA REGISTRAR 
 * UM TOKEN NOVO PARA ACESSAR O 
 * MESMO TOKEN SEM TER Q LOGAR NOVAMENTE*/

@RestController
@RequestMapping(value = "/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthService service;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		//PEGAR O USUARIO LOGADO
		UserSS user = UserService.authenticated();
		String token = jwtUtil.generateToken(user.getUsername());
		response.addHeader("Authorization", "Bearer " + token);
		response.addHeader("access-control-expose-headers", "Authorization");
		return ResponseEntity.noContent().build();
	}

//	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
//	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
//		service.sendNewPassword(objDto.getEmail());
//		return ResponseEntity.noContent().build();
//	}
}
