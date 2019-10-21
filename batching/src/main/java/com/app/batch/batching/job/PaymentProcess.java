package com.app.batch.batching.job;


import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.app.batch.batching.entity.Payments;
import com.app.batch.batching.enumeration.StatusEnum;

@Component
public class PaymentProcess implements ItemProcessor<Payments, Payments>{

	@Override
	public Payments process(Payments item) throws Exception {
		
		item.setStatus(StatusEnum.PAYMENT_OK);
		
		return item;
	}

}
