package com.devjolima.rest;

import java.util.Set;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.devjolima.data.Country;
import com.devjolima.service.CountriesService;

import org.eclipse.microprofile.rest.client.inject.RestClient;

@Path("/country")
@Produces(MediaType.APPLICATION_JSON)
public class CountryRest {

    @Inject
    @RestClient
    private CountriesService countriesService;

    @GET
    @Path("/name/{name}")
    public Set<Country> name(@PathParam("name") String name) {
        return countriesService.getByName(name);
    }

}