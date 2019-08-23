package com.demo.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableAutoConfiguration(exclude=SecurityAutoConfiguration.class)
@ComponentScan("com.demo.ms")
public class MSAuthServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MSAuthServerApplication.class, args);
	}

}
