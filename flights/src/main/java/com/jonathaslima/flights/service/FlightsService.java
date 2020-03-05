package com.jonathaslima.flights.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jonathaslima.flights.to.RequestFlyTO;
import com.jonathaslima.flights.to.Response;
import com.jonathaslima.flights.to.ResponseDetailTO;

@Service
public class FlightsService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public Map<String, Response> findFly(RequestFlyTO flyObjTO) {
		
		String apiUrl = "https://api.skypicker.com/flights?flyFrom=OPO&to=LIS&partner=picky&curr=EUR";
		String json = restTemplate.exchange(apiUrl, HttpMethod.GET, mountHeadersRequisition(), String.class).getBody();
		
		JSONObject jsonResult = new JSONObject(json);
		JSONArray data = jsonResult.getJSONArray("data");
		
		List<ResponseDetailTO> listFly = new ArrayList<ResponseDetailTO>();
		
		String flyFrom = flyObjTO.airportCodeFrom;
		String flyTo = flyObjTO.airportCodeTo;
		String currency = flyObjTO.currency;
		
		
		for(int i=0; i<data.length(); i++) {
			
			ResponseDetailTO detail = new ResponseDetailTO();
			
			Integer price = (Integer)data.getJSONObject(i).get("price");
			String cityNameFrom = (String) data.getJSONObject(i).get("cityFrom");
			String cityNameTo = (String) data.getJSONObject(i).get("cityTo");
			
			detail.setCurrency(currency);
			detail.setCodeFrom(flyFrom);
			detail.setCodeTo(flyTo);
			detail.setCityNameFrom(cityNameFrom);
			detail.setCityNameTo(cityNameTo);
			detail.setPrice(price);
			
			Double bagPrice1 = (Double) data.getJSONObject(i).getJSONObject("bags_price").get("1");
			if(null != data.getJSONObject(i).getJSONObject("bags_price") && !data.getJSONObject(i).getJSONObject("bags_price").isNull("2")) {
				Double bagPrice2 = (Double)data.getJSONObject(i).getJSONObject("bags_price").get("2") ;
				detail.setBag2(bagPrice2);
			}else {
				detail.setBag2(0.0);
			}
			detail.setBag1(bagPrice1);
			listFly.add(detail);
		}
		
		return setupResponse(listFly);
		
	}
	
	private Map<String, Response> setupResponse(List<ResponseDetailTO> listFly) {
		
		Response response = new Response();
		
		OptionalDouble priceAverage =  listFly.stream().mapToInt(ResponseDetailTO::getPrice).average();
		OptionalDouble bag1Average =  listFly.stream().mapToDouble(ResponseDetailTO::getBag1).average();
		OptionalDouble bag2Average =  listFly.stream().mapToDouble(ResponseDetailTO::getBag2).average();

		priceAverage.getAsDouble();
		bag1Average.getAsDouble();
		bag2Average.getAsDouble();
		
		response.currency = listFly.get(0).getCurrency();
		response.name = listFly.get(0).getCityNameFrom();
		response.price_average = priceAverage.getAsDouble();
			
		Map<String, Response> mapFrom = new HashMap<String, Response>();
		mapFrom.put(listFly.get(0).getCodeFrom(), response);
		
		return mapFrom;
	}

	@SuppressWarnings("rawtypes")
	private HttpEntity mountHeadersRequisition() {

		HttpHeaders headers = new HttpHeaders();
		headers.add("user-agent",
				"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		return entity;

	}

}
