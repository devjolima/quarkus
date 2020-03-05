package com.jonathaslima.flights.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jonathaslima.flights.model.Domain;

public interface DomainRepository extends MongoRepository<Domain, Long>{

}
