package com.app.batch.batching.job;

import javax.sql.DataSource;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.database.JdbcCursorItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.app.batch.batching.entity.Payments;
import com.app.batch.batching.util.PaymentsRowMapper;

@Configuration
public class JobConfig {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	public DataSource dataSource;
	
	@Autowired
	private PaymentProcess process;
	
	@Autowired
	private PaymentWriter writer;

	@Bean
	public JdbcCursorItemReader<Payments> reader() {

		final String SQL = "SELECT * FROM PAYMENTS WHERE status = 0 ";

		JdbcCursorItemReader<Payments> reader = new JdbcCursorItemReader<Payments>();
		reader.setDataSource(dataSource);
		reader.setSql(SQL);
		reader.setRowMapper(new PaymentsRowMapper());

		return reader;
	}

	@Bean
	public Step step1() {
		return stepBuilderFactory.get("step1")
				.<Payments, Payments>chunk(10)
				.reader(reader())
				.processor(process)
				.writer(writer)
				.build();
	}
	

	@Bean
	public Job exportUserJob() {
		return jobBuilderFactory.get("paymentJob")
				.incrementer(new RunIdIncrementer())
				.flow(step1())
				.end()
				.build();
	}

}
