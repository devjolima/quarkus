package com.demo.ms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.demo.ms.domain.User;

public interface UserRepository extends JpaRepository<User, String> {
	
	UserDetails findByName(String username);
	
}
