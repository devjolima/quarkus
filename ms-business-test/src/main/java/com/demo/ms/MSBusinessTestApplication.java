package com.demo.ms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@ComponentScan("com.demo.ms")
@EnableAutoConfiguration(exclude=DataSourceAutoConfiguration.class)
@EnableCircuitBreaker
public class MSBusinessTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(MSBusinessTestApplication.class, args);
	}

}
