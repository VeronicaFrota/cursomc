package com.veronicafrota.cursomc.resource;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.veronicafrota.cursomc.dto.EmailDTO;
import com.veronicafrota.cursomc.security.JWTUtil;
import com.veronicafrota.cursomc.security.UserSS;
import com.veronicafrota.cursomc.services.AuthService;
import com.veronicafrota.cursomc.services.UserService;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;

	@Autowired
	private AuthService service;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();						// Get user logged
		String token = jwtUtil.generateToken(user.getUsername());		// Create new token with the user (with the new date)
		response.addHeader("Authorization", "Bearer " + token);			// Add the token to the request response 
		return ResponseEntity.noContent().build();
	}


	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public ResponseEntity<Void> forgot(@Valid @RequestBody EmailDTO objDto) {
		
		service.sendNewPassword(objDto.getEmail());						// To send a new Password
		
		return ResponseEntity.noContent().build();
	}
}
