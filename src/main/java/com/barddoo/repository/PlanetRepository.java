package com.barddoo.repository;

import com.barddoo.model.Planet;
import com.barddoo.model.PageBean;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.InsertOneResult;
import com.mongodb.reactivestreams.client.FindPublisher;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoCollection;
import org.bson.types.ObjectId;
import org.reactivestreams.Publisher;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class PlanetRepository {
    @Inject
    MongoClient mongoClient;

    MongoCollection<Planet> collection;

    @PostConstruct
    private void init() {
        collection = mongoClient
                .getDatabase("star-wars")
                .getCollection("planets", Planet.class);
    }

    public Publisher<InsertOneResult> insertOne(Planet entity) {
        return collection.insertOne(entity);
    }

    public FindPublisher<Planet> find(PageBean page) {
        return collection.find().skip(page.getSize() * (page.getPage() - 1)).limit(page.getSize());
    }

    public Publisher<Planet> findById(String id) {
        return collection.find(Filters.eq("_id", new ObjectId(id))).first();
    }

    public Publisher<Planet> findByName(String name) {
        return collection.find(Filters.eq("name", name)).first();
    }

    public Publisher<Planet> removeById(String id) {
        return collection.findOneAndDelete(Filters.eq("_id", new ObjectId(id)));
    }
    public Publisher<Void> remove() {
        return collection.drop();
    }

}
