package com.jonathaslima.flights.rest;

import java.nio.charset.Charset;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.jonathaslima.flights.to.RequestTO;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class FlightsRestTest {
	
	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(), 
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));
	
	@Autowired
    private MockMvc mvc;
	
	@Test
	public void findFly() throws Exception {
		
	    RequestTO obj = new RequestTO();
	    obj.airportCodeFrom = "FOR";
	    obj.airportCodeTo = "LIS";
	    obj.currency = "EUR";
		String apiUrl = "https://api.skypicker.com/flights?flyFrom="+obj.airportCodeFrom+"&to="+obj.airportCodeTo+"&partner=picky&curr="+obj.currency;
	 
		ObjectMapper mapper = new ObjectMapper();
	    mapper.configure(SerializationFeature.WRAP_ROOT_VALUE, false);
	    ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
	    String requestJson=ow.writeValueAsString(obj);

	    mvc.perform(MockMvcRequestBuilders.post("/api/fly").contentType(APPLICATION_JSON_UTF8)
	        .content(requestJson)).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void deleteAllRequests() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/deleteRquests")).andExpect(MockMvcResultMatchers.status().isOk());
	}
	
	@Test
	public void getAllRequests() throws Exception {
		mvc.perform(MockMvcRequestBuilders.get("/api/allRequests")).andExpect(MockMvcResultMatchers.status().isOk());
	}

}
