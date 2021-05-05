package com.barddoo.cache;

import com.barddoo.integration.model.PlanetsSwapiResponse;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Singleton
public class PlanetCache implements CacheBean<Integer, PlanetsSwapiResponse> {

    private final Cache<Integer, PlanetsSwapiResponse> cache;

    public PlanetCache() {
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.HOURS)
                .build();
    }

    @Override
    public void put(Integer key, PlanetsSwapiResponse value) {
        cache.put(key, value);
    }

    @Override
    public Optional<PlanetsSwapiResponse> get(Integer key) {
        return Optional.ofNullable(cache.getIfPresent(key));
    }
}