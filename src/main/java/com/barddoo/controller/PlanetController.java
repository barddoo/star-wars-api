package com.barddoo.controller;

import com.barddoo.PlanetService;
import com.barddoo.model.PageBean;
import com.barddoo.model.Planet;
import com.barddoo.rest.model.PlanetBuilder;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.MutableHttpResponse;
import io.micronaut.http.annotation.*;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Maybe;
import lombok.extern.log4j.Log4j2;

import javax.inject.Inject;
import javax.validation.Valid;

@Controller(value = "/planet", produces = "application/json")
@Log4j2
public class PlanetController {

    @Inject
    PlanetService planetService;

    @Get
    public Flowable<Planet> findByPage(@Valid @RequestBean PageBean pageBean) {
        log.info("Find planets");
        return planetService.findByPage(pageBean);
    }

    @Get(uri = "/name/{name}")
    public Maybe<Planet> getByName(@PathVariable String name) {
        log.info("Find planet by name");
        return planetService.findByName(name);
    }

    @Get(uri = "/{id}")
    public Maybe<Planet> getById(@PathVariable String id) {
        log.info("Find planet by id");
        return planetService.findById(id);
    }

    @Post
    public Maybe<Planet> create(@Valid @Body PlanetRequest planet) {
        log.info("Insert planet");
        return planetService.insert(PlanetBuilder.toPlanet(planet));
    }

    @Delete(uri = "/{id}")
    public @NonNull Maybe<MutableHttpResponse<Planet>> delete(@PathVariable String id) {
        log.info("Delete planets");
        return planetService.removeById(id)
                .map(HttpResponse::ok);
    }

    @Delete
    public MutableHttpResponse<Object> delete() {
        log.info("Delete planets");
        planetService.remove();
        return HttpResponse.ok();
    }
}