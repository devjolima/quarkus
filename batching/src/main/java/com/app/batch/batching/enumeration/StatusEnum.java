package com.app.batch.batching.enumeration;

public enum StatusEnum {

	
	PAYMENT_OK("PAYMENT_OK"), PAYMENT_FAIL("PAYMENT_FAIL");
	
	private String value;
	
	private StatusEnum(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
