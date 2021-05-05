package com.barddoo.integration.model;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Introspected
public class PlanetSwapi {
    private List<String> films;
    private String edited;
    private String created;
    private String climate;
    private String rotationPeriod;
    private String url;
    private String population;
    private String orbitalPeriod;
    private String surfaceWater;
    private String diameter;
    private String gravity;
    private String name;
    private List<String> residents;
    private String terrain;
}