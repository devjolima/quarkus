package com.devjolima.rest;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import com.devjolima.data.RequestData;
import com.devjolima.service.ContactService;




@Path("/contacts")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ContactRest {

    @Inject ContactService contactService;

    @GET
    public List<RequestData> list() {
        return contactService.list();
    }

    @POST
    public List<RequestData> add(RequestData request){

        contactService.saveRequest(request);
        return list();
    }


}