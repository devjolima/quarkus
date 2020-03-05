package com.jonathaslima.flights.to;

public class ResponseDetailTO {
	
	private String codeFrom;
	private String codeTo;
	private String cityNameFrom;
	private String cityNameTo;
	private String currency;
	private Integer price;
	private Double bag1;
	private Double bag2;
	
	public Double getBag1() {
		return bag1;
	}
	public void setBag1(Double bag1) {
		this.bag1 = bag1;
	}
	public Double getBag2() {
		return bag2;
	}
	public void setBag2(Double bag2) {
		this.bag2 = bag2;
	}
	public String getCityNameFrom() {
		return cityNameFrom;
	}
	public void setCityNameFrom(String cityNameFrom) {
		this.cityNameFrom = cityNameFrom;
	}
	public String getCityNameTo() {
		return cityNameTo;
	}
	public void setCityNameTo(String cityNameTo) {
		this.cityNameTo = cityNameTo;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public Integer getPrice() {
		return price;
	}
	public void setPrice(Integer price) {
		this.price = price;
	}
	public String getCodeFrom() {
		return codeFrom;
	}
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}
	public String getCodeTo() {
		return codeTo;
	}
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}
}
