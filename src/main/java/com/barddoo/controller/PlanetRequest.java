package com.barddoo.controller;

import io.micronaut.core.annotation.Introspected;
import lombok.Data;

@Data
@Introspected
public class PlanetRequest {
    private String name;
    private String climate;
    private String terrain;
}
