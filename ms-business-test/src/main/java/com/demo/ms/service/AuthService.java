package com.demo.ms.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@FeignClient(value = "authService", url = "http://localhost:9092/", fallbackFactory=AuthServiceImpl.class)
public interface AuthService {

	@RequestMapping(value="/api/feign-test", method = RequestMethod.GET, produces = "application/json")
	String getAuth();

}
