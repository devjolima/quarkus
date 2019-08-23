package com.demo.ms.erureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class MSEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MSEurekaServerApplication.class, args);
	}

}
