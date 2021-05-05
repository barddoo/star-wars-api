package com.barddoo.integration;

import com.barddoo.cache.FilmsCache;
import com.barddoo.cache.PlanetCache;
import com.barddoo.integration.model.FilmSwapiResponse;
import com.barddoo.integration.model.PlanetSwapi;
import com.barddoo.integration.model.PlanetsSwapiResponse;
import io.micronaut.context.annotation.Value;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.client.HttpClient;
import io.micronaut.http.uri.UriBuilder;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

@Singleton
@Log4j2
public class SwapiClient {

    @Inject
    private HttpClient httpClient;

    @Value("${swapi.endpoint}")
    private String enpoint;

    @Inject
    private FilmsCache filmsCache;

    @Inject
    private PlanetCache planetCache;

    public @NonNull Maybe<PlanetsSwapiResponse> getPlanets(AtomicInteger page) {
        var uri = UriBuilder.of(enpoint).path("/planets/").queryParam("page", page.get()).build();
        HttpRequest<?> req = HttpRequest.GET(uri);
        Optional<PlanetsSwapiResponse> planetSwapi = planetCache.get(page.get());
        if (planetSwapi.isPresent()) {
            log.info("Cache hit | Planet {}", planetSwapi.get());
            return Maybe.fromOptional(planetSwapi);
        }
        log.info("Access endpoint {}", uri.toString());
        return Maybe.fromPublisher(httpClient.retrieve(req, PlanetsSwapiResponse.class))
                .doOnSuccess(resp -> planetCache.put(page.get(), resp));
    }

    public Maybe<PlanetSwapi> getPlanetsByName(String name) {
        var page = new AtomicInteger(1);
        var foundOrEnd = new AtomicReference<>(false);
        Flowable<PlanetSwapi> planetSwapiMaybe = Flowable.fromCallable(() -> getPlanets(page))
                .flatMapMaybe(planetsSwapiResponsePublisher -> planetsSwapiResponsePublisher)
                .mapOptional(swapPlanets -> {
                    page.incrementAndGet();
                    Optional<PlanetSwapi> optional = swapPlanets.getResults()
                            .stream()
                            .filter(swapPlanet -> Objects.equals(swapPlanet.getName(), name))
                            .findFirst();
                    if (optional.isPresent()) {
                        foundOrEnd.set(true);
                    } else {
                        foundOrEnd.set(swapPlanets.getNext() == null);
                    }
                    return optional;
                })
                .repeatUntil(foundOrEnd::get);
        return planetSwapiMaybe.firstElement();
    }

    public Maybe<FilmSwapiResponse> getFilm(String film) {
        HttpRequest<?> req = HttpRequest.GET(UriBuilder.of(film).build());

        Optional<FilmSwapiResponse> swapiResponse = filmsCache.get(film);
        if (swapiResponse.isPresent()) {
            log.info("Cache hit | Film {}", swapiResponse.get());
            return Maybe.fromOptional(swapiResponse);
        }
        log.info("Access endpoint {}", film);
        return Maybe.fromPublisher(httpClient.retrieve(req, FilmSwapiResponse.class))
                .doOnSuccess(filmSwapiResponse -> filmsCache.put(film, filmSwapiResponse))
                .doOnError(throwable -> log.error("Error on SWAPI integration", throwable));
    }
}
