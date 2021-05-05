package com.barddoo;

import com.barddoo.cache.FilmsCache;
import com.barddoo.cache.PlanetCache;
import com.barddoo.integration.SwapiClient;
import com.barddoo.integration.model.FilmSwapiResponse;
import com.barddoo.model.PageBean;
import com.barddoo.model.Planet;
import com.barddoo.repository.PlanetRepository;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Single;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.stream.Collectors;

@Singleton
@Log4j2
public class PlanetService {

    @Inject
    private PlanetRepository planetRepository;
    @Inject
    private SwapiClient swapiClient;


    private Flowable<String> getFilmsBy(Planet planet) {
        return swapiClient.getPlanetsByName(planet.getName())
                .flatMapPublisher(planetSwapi -> Flowable.fromIterable(planetSwapi.getFilms()))
                .flatMapMaybe(filmUrl -> swapiClient.getFilm(filmUrl))
                .map(FilmSwapiResponse::getTitle)
                .doOnError(throwable -> log.error("Error get films of planet", throwable));
    }

    public Maybe<Planet> insert(Planet entity) {
        return getFilmsBy(entity)
                .collect(Collectors.toList())
                .doOnSuccess(entity::setFilms)
                .flatMapMaybe(ignore -> Maybe.fromPublisher(planetRepository.insertOne(entity)))
                .doOnSuccess(insertResult -> entity.setId(insertResult.getInsertedId().asObjectId().getValue()))
                .map(ignore -> entity)
                .doOnError(throwable -> {
                    log.error("Error on insert planet", throwable);
                });
    }

    public Maybe<Planet> findById(String id) {
        return Maybe.fromPublisher(planetRepository.findById(id));
    }

    public Flowable<Planet> findByPage(PageBean page) {
        return Flowable.fromPublisher(planetRepository.find(page));
    }

    public Maybe<Planet> findByName(String name) {
        return Maybe.fromPublisher(planetRepository.findByName(name));
    }

    public Maybe<Planet> removeById(String id) {
        return Maybe.fromPublisher(planetRepository.removeById(id));
    }
    public @NonNull Single<Void> remove() {
        return Single.fromPublisher(planetRepository.remove());
    }
}
