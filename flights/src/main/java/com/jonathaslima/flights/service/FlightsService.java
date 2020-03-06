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

import com.jonathaslima.flights.to.JsonResultTO;
import com.jonathaslima.flights.to.RequestTO;
import com.jonathaslima.flights.to.ResponseTO;

@Service
public class FlightsService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public Map<String, ResponseTO> findFly(RequestTO flyObjTO) {
		
		String json = getFlyInfoJson(flyObjTO.airportCodeFrom, flyObjTO.airportCodeTo, flyObjTO.currency);
		
		JSONObject jsonResult = new JSONObject(json);
		JSONArray data = jsonResult.getJSONArray("data");
		
		List<JsonResultTO> listFly = new ArrayList<JsonResultTO>();
		
		String flyFrom = flyObjTO.airportCodeFrom;
		String flyTo = flyObjTO.airportCodeTo;
		String currency = flyObjTO.currency;
		
		for(int i=0; i<data.length(); i++) {
			setupJsonToResponseDetail(data, listFly, flyFrom, flyTo, currency, i);
		}
		
		return setupResponse(listFly);
		
	}

	private void setupJsonToResponseDetail(JSONArray data, List<JsonResultTO> listFly, String flyFrom, String flyTo,
			String currency, int i) {
		
		JsonResultTO detail = new JsonResultTO();
		
		detail.setCurrency(currency);
		detail.setCodeFrom(flyFrom);
		detail.setCodeTo(flyTo);
		detail.setCityNameFrom((String) data.getJSONObject(i).get("cityFrom"));
		detail.setCityNameTo((String) data.getJSONObject(i).get("cityTo"));
		detail.setPrice((Integer)data.getJSONObject(i).get("price"));
		detail.setBag1((Double) data.getJSONObject(i).getJSONObject("bags_price").get("1"));
		
		if(null != data.getJSONObject(i).getJSONObject("bags_price") && !data.getJSONObject(i).getJSONObject("bags_price").isNull("2")) {
			detail.setBag2((Double)data.getJSONObject(i).getJSONObject("bags_price").get("2"));
		}else {
			detail.setBag2(0.0);
		}
		listFly.add(detail);
	}

	private String getFlyInfoJson(String airportCodeFrom, String airportCodeTo, String currency) {
		String apiUrl = "https://api.skypicker.com/flights?flyFrom="+airportCodeFrom+"&to="+airportCodeTo+"&partner=picky&curr="+currency;
		String json = restTemplate.exchange(apiUrl, HttpMethod.GET, mountHeadersRequisition(), String.class).getBody();
		return json;
	}
	
	private Map<String, ResponseTO> setupResponse(List<JsonResultTO> listFly) {
		
		ResponseTO response = new ResponseTO();
		
		response.currency = listFly.get(0).getCurrency();
		response.name = listFly.get(0).getCityNameFrom();
		response.price_average = getPriceAverage(listFly);
			
		Map<String, ResponseTO> mapFrom = new HashMap<String, ResponseTO>();
		mapFrom.put(listFly.get(0).getCodeFrom(), response);
		
		return mapFrom;
	}
	
	public Double getPriceAverage(List<JsonResultTO> listFly) {
		OptionalDouble priceAverage = listFly.stream().mapToInt(JsonResultTO::getPrice).average();
		return priceAverage.getAsDouble();
	}
	
	public Double getBag1Average(List<JsonResultTO> listFly) {
		OptionalDouble bag1Average = listFly.stream().mapToDouble(JsonResultTO::getBag1).average();
		return bag1Average.getAsDouble();
	}
	
	public Double getBag2Average(List<JsonResultTO> listFly) {
		OptionalDouble bag2Average = listFly.stream().mapToDouble(JsonResultTO::getBag2).average();
		return bag2Average.getAsDouble();
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
