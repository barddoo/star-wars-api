package com.barddoo.integration.model;

import io.micronaut.core.annotation.Introspected;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Introspected
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlanetsSwapiResponse {
    private Integer count;
    private String next;
    private String previous;
    private List<PlanetSwapi> results;
}
