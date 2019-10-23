package com.app.batch.batching;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.launch.support.CommandLineJobRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.app.batch.batching.entity.Payments;
import com.app.batch.batching.enumeration.StatusEnum;
import com.app.batch.batching.repository.PaymentsRepository;

@SpringBootApplication
@EnableBatchProcessing
@ComponentScan("com.app.batch.batching")
@EntityScan("com.app.batch.batching.entity")
@EnableJpaRepositories("com.app.batch.batching.repository")
public class BatchingApplication {

	@Autowired
	private PaymentsRepository paymentsRepository;
	
	public static void main(String[] args) {
		SpringApplication.run(BatchingApplication.class, args);
	}


}
