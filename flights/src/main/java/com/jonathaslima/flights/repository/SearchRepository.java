package com.jonathaslima.flights.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.jonathaslima.flights.model.Search;

public interface SearchRepository extends MongoRepository<Search, Long>{

}
