package com.jonathaslima.flights.to;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RequestTO implements Serializable{
	
	private static final long serialVersionUID = -1195848056126953816L;
	
	public String airportCodeFrom;
	public String airportCodeTo;
	@JsonProperty(required=false)
	public String initialDateFly;
	@JsonProperty(required=false)
	public String finalDateFly;
	public String currency;

}
