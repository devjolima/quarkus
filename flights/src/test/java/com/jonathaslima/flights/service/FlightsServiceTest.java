package com.jonathaslima.flights.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.jonathaslima.flights.model.Search;
import com.jonathaslima.flights.repository.SearchRepository;
import com.jonathaslima.flights.to.JsonResultTO;

@RunWith(MockitoJUnitRunner.Silent.class)
public class FlightsServiceTest {
	
	@Mock
	SearchRepository searchRepository;
	
	@InjectMocks
	FlightsService service;
	
	@Test
	public void when_save_search_return_search() {
		
		Search search = new Search();
		search.setCurrency("EUR");
		search.setFromCode("OPO");
		search.setToCode("FOR");
		search.setDate(new Date());
	    
	    when(searchRepository.save(search)).thenReturn(search);
	}
	
	@Test
	public void averagePriceTest() {
		
		List<JsonResultTO> listFly = new ArrayList<>();
		JsonResultTO to1 = new JsonResultTO();
		to1.setPrice(50);
		JsonResultTO to2 = new JsonResultTO();
		to2.setPrice(30);
		listFly.add(to1);listFly.add(to2);
		
		Double average = service.getPriceAverage(listFly);
		assertEquals(40, average);
		assertNotEquals(40.1, average);
	}
	
	@Test
	public void averageBag1Test() {
		
		List<JsonResultTO> listFly = new ArrayList<>();
		JsonResultTO to1 = new JsonResultTO();
		to1.setBag1(50.0);
		JsonResultTO to2 = new JsonResultTO();
		to2.setBag1(30.0);
		
		listFly.add(to1);listFly.add(to2);
		Double average = service.getBag1Average(listFly);
		
		assertEquals(40, average);
		assertNotEquals(40.1, average);
	}
	
	@Test
	public void averageBag2Test() {
		
		List<JsonResultTO> listFly = new ArrayList<>();
		JsonResultTO to1 = new JsonResultTO();
		to1.setBag2(50.0);
		JsonResultTO to2 = new JsonResultTO();
		to2.setBag2(30.0);
		
		listFly.add(to1);listFly.add(to2);
		Double average = service.getBag2Average(listFly);
		
		assertEquals(40, average);
		assertNotEquals(40.1, average);
	}

}
