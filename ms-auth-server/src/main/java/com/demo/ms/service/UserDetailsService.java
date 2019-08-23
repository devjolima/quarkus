package com.demo.ms.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.demo.ms.repository.UserRepository;


@Service
@Transactional
public class UserDetailsService {
	
	UserRepository userRepository;
	
}