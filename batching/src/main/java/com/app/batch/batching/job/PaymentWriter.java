package com.app.batch.batching.job;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.batch.batching.entity.Payments;
import com.app.batch.batching.repository.PaymentsRepository;

@Component
public class PaymentWriter implements ItemWriter<Payments>{
	
	@Autowired
	private PaymentsRepository paymentsRepository;
	
	@PersistenceContext
	private EntityManager em;

	@Override
	public void write(List<? extends Payments> items) throws Exception {
	
		
		em.merge(items.get(0));
		
	}
	
	private Payments parsePayment(Payments payments) {
		
		Payments pay = new Payments();
		pay.setClientCode(payments.getClientCode());
		pay.setId(payments.getId());
		pay.setStatus(payments.getStatus());
		pay.setValue(payments.getValue());
		
		return pay;
	}

}
