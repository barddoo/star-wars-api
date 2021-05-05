package com.barddoo.repository;

import com.barddoo.model.Planet;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class Mongo {
    @Inject
    MongoClient mongoClient;


    private MongoCollection<Planet> getCollection() {
        return mongoClient
                .getDatabase("micronaut")
                .getCollection("users", Planet.class);
    }
}