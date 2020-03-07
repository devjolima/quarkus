package com.jonathaslima.flights.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jonathaslima.flights.repository.SearchRepository;
import com.jonathaslima.flights.rest.exception.ApiError;
import com.jonathaslima.flights.rest.exception.FlyNotFoundException;
import com.jonathaslima.flights.service.FlightsService;
import com.jonathaslima.flights.to.RequestTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@RestController
@CrossOrigin
@RequestMapping("/api")
@Api("Flights pricing API")
public class FlightsRest {
	
	private static final Logger log = LoggerFactory.getLogger(FlightsRest.class);

	
	@Autowired
	private FlightsService service;
	
	@Autowired
	private SearchRepository repository;
	
	@SuppressWarnings({ "static-access", "rawtypes" })
	@PostMapping("/fly")
	@ApiOperation(value = "End Point for search flyghts")
	public ResponseEntity findFly(@RequestBody RequestTO request) throws FlyNotFoundException{
		
		log.info("Request values: Aiport from : [ "+request.airportCodeFrom +", Aiport to :" +request.airportCodeTo+" ]");
		
		try {
			
			return new ResponseEntity(HttpStatus.OK).ok().body(service.findFly(request));
			
		} catch (Exception e) {
			ApiError apiError = new ApiError();
			apiError.setMessage("Try Again with anothers aiports code please");
			apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(apiError, apiError.getStatus());
		}
	}
	
	@SuppressWarnings({ "static-access", "rawtypes" })
	@GetMapping("/deleteRquests")
	public ResponseEntity deleteAll() {
		
		log.info(" [ Operation delete is called. ]");
		
		try {
			
			repository.deleteAll();
			return new ResponseEntity(HttpStatus.OK).ok().body("Object(s) was removed");
			
		} catch (Exception e) {
			ApiError apiError = new ApiError();
			apiError.setMessage("Operation failed");
			apiError.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(apiError, apiError.getStatus());
		}
	}
	
	@SuppressWarnings({ "static-access", "rawtypes" })
	@GetMapping("/allRequests")
	public ResponseEntity all() {
		
		log.info(" [ Operation get all is called. ]");
		
		return new ResponseEntity(HttpStatus.OK).ok().body(repository.findAll());
	}

}
