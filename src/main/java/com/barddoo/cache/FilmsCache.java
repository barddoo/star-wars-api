package com.barddoo.cache;

import com.barddoo.integration.model.FilmSwapiResponse;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import javax.inject.Singleton;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Singleton
public class FilmsCache implements CacheBean<String, FilmSwapiResponse> {

    private final Cache<String, FilmSwapiResponse> cache;

    public FilmsCache() {
        this.cache = CacheBuilder.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(10, TimeUnit.HOURS)
                .build();
    }

    @Override
    public void put(String key, FilmSwapiResponse value) {
        cache.put(key, value);
    }

    @Override
    public Optional<FilmSwapiResponse> get(String key) {
        return Optional.ofNullable(cache.getIfPresent(key));
    }
}