package com.jonathaslima.flights.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jonathaslima.flights.service.FlightsService;
import com.jonathaslima.flights.to.RequestFlyTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api("Flights pricing API")
public class FlightsRest {
	
	@Autowired
	private FlightsService service;
	
	@SuppressWarnings({ "static-access", "rawtypes" })
	@PostMapping("/fly")
	@ApiOperation(value = "End Point for search flyghts")
	public ResponseEntity findFly(@RequestBody RequestFlyTO request){
		
		return new ResponseEntity(HttpStatus.OK).ok().body(service.findFly(request));
	}

}
