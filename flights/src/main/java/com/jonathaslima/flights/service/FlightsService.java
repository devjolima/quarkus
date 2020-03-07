package com.jonathaslima.flights.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jonathaslima.flights.model.Search;
import com.jonathaslima.flights.repository.SearchRepository;
import com.jonathaslima.flights.to.JsonResultTO;
import com.jonathaslima.flights.to.RequestTO;
import com.jonathaslima.flights.to.ResponseTO;

@Service
public class FlightsService {
	
	private static final Logger log = LoggerFactory.getLogger(FlightsService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private SearchRepository repository;
	
	
	/**
	 * Method return all flyghts mapped according request object
	 * 
	 * @param airportCodeFrom - request Aiport from
	 * @param airportCodeTo - request Aiport to
	 * @param currency - request currency 
	 * @return - JSONArray with all flights finded to the request
	 * 
	 */
	public JSONArray getFlyInfoJson(String airportCodeFrom, String airportCodeTo, String currency) {
		
		String apiUrl = "https://api.skypicker.com/flights?flyFrom="+airportCodeFrom+"&to="+airportCodeTo+"&partner=picky&curr="+currency;
		
		String json = restTemplate.exchange(apiUrl, HttpMethod.GET, mountHeadersRequisition(), String.class).getBody();
		
		JSONObject jsonResult = new JSONObject(json);
		
		JSONArray data = jsonResult.getJSONArray("data");
		
		log.info("JSON Called : [ "+apiUrl+" ]");
		
		saveRquest(airportCodeFrom,airportCodeTo,currency);
		
		return data;
	}
	
	
	private void saveRquest(String airportCodeFrom, String airportCodeTo, String currency) {
		
		Search search = new Search();
		search.setCurrency(currency);
		search.setFromCode(airportCodeFrom);
		search.setToCode(airportCodeTo);
		search.setDate(new Date());
		repository.save(search);
		
	}


	public Map<String, ResponseTO> findFly(RequestTO request) {
		
		List<JsonResultTO> listFly = new ArrayList<JsonResultTO>();
		
		process(request, listFly);
		
		return setupResponse(listFly);
		
	}


	private void process(RequestTO flyObjTO, List<JsonResultTO> listFly) {
		JSONArray data = getFlyInfoJson(flyObjTO.airportCodeFrom, flyObjTO.airportCodeTo, flyObjTO.currency);
		
		String flyFrom = flyObjTO.airportCodeFrom;
		
		String flyTo = flyObjTO.airportCodeTo;
		
		String currency = flyObjTO.currency;
		
		log.info("Flygths mapped size : [ "+data.length()+" ]");
		
		for(int i=0; i<data.length(); i++) {
			
			setupJsonToResponseDetail(data, listFly, flyFrom, flyTo, currency, i);
			
		}
	}


	/**
	 * 
	 * @param data
	 * @param listFly
	 * @param flyFrom
	 * @param flyTo
	 * @param currency
	 * @param i
	 */
	private void setupJsonToResponseDetail(JSONArray data, List<JsonResultTO> listFly, String flyFrom, String flyTo,
			String currency, int i) {
		
		JsonResultTO detail = new JsonResultTO();
		
		detail.setCurrency(currency);
		detail.setCodeFrom(flyFrom);
		detail.setCodeTo(flyTo);
		detail.setCityNameFrom((String) data.getJSONObject(i).get("cityFrom"));
		detail.setCityNameTo((String) data.getJSONObject(i).get("cityTo"));
		detail.setPrice((Integer)data.getJSONObject(i).get("price"));
		
		if(null != data.getJSONObject(i).getJSONObject("bags_price") && !data.getJSONObject(i).getJSONObject("bags_price").isNull("1")) {
			detail.setBag1((Double) data.getJSONObject(i).getJSONObject("bags_price").get("1"));
		}else {
			detail.setBag1(0.0);
		}
		
		if(null != data.getJSONObject(i).getJSONObject("bags_price") && !data.getJSONObject(i).getJSONObject("bags_price").isNull("2")) {
			detail.setBag2((Double)data.getJSONObject(i).getJSONObject("bags_price").get("2"));
		}else {
			detail.setBag2(0.0);
		}
		listFly.add(detail);
	}

	
	/**
	 * Method to setup response object to client/swagger
	 * 
	 * @param listFly - List of flights converted to TO
	 * @return
	 */
	private Map<String, ResponseTO> setupResponse(List<JsonResultTO> listFly) {
		
		ResponseTO responseFrom = new ResponseTO();
		ResponseTO responseTo = new ResponseTO();
		
		responseFrom.currency = listFly.get(0).getCurrency();
		responseFrom.name = listFly.get(0).getCityNameFrom();
		responseFrom.price_average = round(getPriceAverage(listFly),2);
		
		Map<String, Double> bagsFrom = new HashMap<String, Double>();
		bagsFrom.put("bag1_average", round(getBag1Average(listFly),1));
		bagsFrom.put("bag2_average", round(getBag2Average(listFly),1));
		responseFrom.setBags_price(bagsFrom);
		
		responseTo.currency = listFly.get(0).getCurrency();
		responseTo.name = listFly.get(0).getCityNameTo();
		responseTo.price_average = round(getPriceAverage(listFly),2);
		
		Map<String, Double> bagsTo = new HashMap<String, Double>();
		bagsTo.put("bag1_average", round(getBag1Average(listFly),2));
		bagsTo.put("bag2_average", round(getBag2Average(listFly),2));
		responseTo.setBags_price(bagsTo);
			
		Map<String, ResponseTO> mapResponse = new HashMap<String, ResponseTO>();
		mapResponse.put(listFly.get(0).getCodeFrom(), responseFrom);
		mapResponse.put(listFly.get(0).getCodeTo(), responseTo);
		
        	
		return mapResponse;
	}
	
	public double round(double input, int scale) {
        BigDecimal bigDecimal = new BigDecimal(input).setScale(scale, RoundingMode.HALF_EVEN);
        return bigDecimal.doubleValue();
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
