package com.demo.ms.controller;

import java.util.Arrays;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.ms.domain.AutenticatioVO;
import com.demo.ms.security.AuthenticationRequest;
import com.demo.ms.security.CustomAuthenticateProvider;
import com.demo.ms.security.JwtTokenProvider;

import io.jsonwebtoken.Jws;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class AuthController {
	
	@Autowired
	private CustomAuthenticateProvider customAuthenticateProvider;

	@Autowired
	private JwtTokenProvider jwtTokenProvider;

	@PostMapping("/auth")
	public ResponseEntity<String> signin(@RequestBody AuthenticationRequest data) {
		String token="";
		try {
			token = jwtTokenProvider.createToken(data.getUsername(),   Arrays.asList("ADMIN"));
			return ResponseEntity.ok().body(token);
			
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/get-name")
	public ResponseEntity<String> getName() {
		return ResponseEntity.ok().body("OK FEIGN");
	}
	
	@GetMapping("/me")
	public ResponseEntity<AutenticatioVO> me(@RequestHeader (name="Authorization") String token) {
		
		AutenticatioVO auth = jwtTokenProvider.getUsername(token);
		return ResponseEntity.ok().body(auth);
	}
	
	@GetMapping("/feign-test")
	public ResponseEntity<String> feignTest() {
		
		try {
			
			AutenticatioVO auth = jwtTokenProvider.getUsername("");
			return ResponseEntity.ok().body(auth.name);
			
		} catch (Exception e) {
			return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
}
