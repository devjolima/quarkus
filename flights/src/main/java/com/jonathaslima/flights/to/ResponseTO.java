package com.jonathaslima.flights.to;

import java.util.Map;

public class ResponseTO {
	
	public String name;
	public String currency;
	public double price_average;
	private Map<String, Double> bags_price;
	
	public Map<String, Double> getBags_price() {
		return bags_price;
	}
	public void setBags_price(Map<String, Double> bags_price) {
		this.bags_price = bags_price;
	}

}
