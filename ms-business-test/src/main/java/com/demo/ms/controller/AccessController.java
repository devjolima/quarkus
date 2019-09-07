package com.demo.ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.ms.service.AuthService;

@RestController
@RequestMapping("/api")
public class AccessController {
	
	@Autowired
	private AuthService service;
	
	@GetMapping("/zull-test")
	public ResponseEntity<String> getName() {
		return ResponseEntity.ok().body("TESTE ZUUL OK");
	}
	
	@GetMapping("/me")
	public ResponseEntity<String> getName(@RequestHeader (name="Authorization") String token) {
		return ResponseEntity.ok().body(service.getAuth());
	}

}
