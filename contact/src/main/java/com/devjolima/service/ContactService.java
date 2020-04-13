package com.devjolima.service;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.devjolima.data.RequestData;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;

import org.bson.Document;


@ApplicationScoped
public class ContactService {

    @Inject MongoClient mongoClient;

    public void saveRequest(final RequestData request) {
        Document document = new Document()
                .append( "code", request.getCode());
        getCollection().insertOne(document);

    }

    public List<RequestData> list(){

        List<RequestData> list = new ArrayList<>();
        MongoCursor<Document> cursor = getCollection().find().iterator();

        try {
            while(cursor.hasNext()){
                Document document = cursor.next();
                RequestData data = new RequestData();
                data.setCode(document.getString("code"));
                list.add(data);
            }
        } catch (Exception e) {
           cursor.close();
        }

        return list;
    }
 
    private MongoCollection getCollection(){
        return mongoClient.getDatabase("contacts").getCollection("contact");
    }

}