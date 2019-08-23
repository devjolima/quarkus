package com.demo.ms.service;

import org.springframework.stereotype.Component;

import feign.hystrix.FallbackFactory;

@Component
public class AuthServiceImpl implements FallbackFactory<AuthService>  {


	@Override
	public AuthService create(Throwable cause) {
		// TODO Auto-generated method stub
		return new AuthService() {
			
			@Override
			public String getAuth() {
				
				return "INVOKING LOCAL STORAGE ..."+cause.getMessage();
			}
		};
	}

}
