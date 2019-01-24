package com.veronicafrota.cursomc.resource;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.veronicafrota.cursomc.security.JWTUtil;
import com.veronicafrota.cursomc.security.UserSS;
import com.veronicafrota.cursomc.services.UserService;

@RestController
@RequestMapping(value="/auth")
public class AuthResource {

	@Autowired
	private JWTUtil jwtUtil;

	@RequestMapping(value = "/refresh_token", method = RequestMethod.POST)
	public ResponseEntity<Void> refreshToken(HttpServletResponse response) {
		UserSS user = UserService.authenticated();						// Get user logged
		String token = jwtUtil.generateToken(user.getUsername());		// Create new token with the user (with the new date)
		response.addHeader("Authorization", "Bearer " + token);			// Add the token to the request response 
		return ResponseEntity.noContent().build();
	}

}
