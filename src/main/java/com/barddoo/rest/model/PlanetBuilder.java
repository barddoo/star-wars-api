package com.barddoo.rest.model;

import com.barddoo.controller.PlanetRequest;
import com.barddoo.model.Planet;


public class PlanetBuilder {
    private PlanetBuilder() {
    }

    public static Planet toPlanet(PlanetRequest planetRequest) {
        return Planet.builder()
                .climate(planetRequest.getClimate())
                .terrain(planetRequest.getTerrain())
                .name(planetRequest.getName())
                .build();
    }
}
