package com.app.batch.batching.job;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import javax.sql.DataSource;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.batch.batching.entity.Payments;
import com.app.batch.batching.enumeration.StatusEnum;
import com.app.batch.batching.repository.PaymentsRepository;

@Component
public class PaymentWriter implements ItemWriter<Payments> {

	@Autowired
	private DataSource dataSource;

	@Autowired
	private PaymentsRepository repo;

	@Override
	public void write(List<? extends Payments> items) throws Exception {

		List<Payments> list = new ArrayList<Payments>();
		for (int i = 0; i < 50; i++) {
			Payments payments = new Payments();
			payments.setClientCode("XXX000");
			payments.setStatus(StatusEnum.PAYMENT_OK);
			payments.setClientCode("200");
			list.add(payments);
		}

		ExecutorService es = Executors.newFixedThreadPool(20);
		ExecutorCompletionService<String> ecs = new ExecutorCompletionService<>(es);
		Future<String> future = es.submit(new JobCallable(list));
		String output = future.get();

	}

	private Payments parsePayment(Payments payments) {

		Payments pay = new Payments();
		pay.setClientCode(payments.getClientCode());
		pay.setId(payments.getId());
		pay.setStatus(payments.getStatus());
		pay.setValue(payments.getValue());

		return pay;
	}

	public class JobCallable implements Callable<String> {

		private List<Payments> statements;

		public JobCallable(List<Payments> list) {
			this.statements = list;
		}

		@Override
		public String call() throws Exception {

			for (Payments insert : statements) {
				repo.save(insert);
			}

			return "";
		}

	}

}
