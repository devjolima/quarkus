package com.jonathaslima.flights.to;

import java.io.Serializable;

public class RequestFlyTO implements Serializable{
	
	private static final long serialVersionUID = -1195848056126953816L;
	
	public String airportCodeFrom;
	public String airportCodeTo;
	public String initialDateFly;
	public String finalDateFly;
	public String currency;

}
