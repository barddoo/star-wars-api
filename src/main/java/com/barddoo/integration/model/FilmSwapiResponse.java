package com.barddoo.integration.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FilmSwapiResponse {
    private String edited;
    private String director;
    private String created;
    private List<String> vehicles;
    private String openingCrawl;
    private String title;
    private String url;
    private List<String> characters;
    private Integer episodeId;
    private List<String> planets;
    private String releaseDate;
    private List<String> starships;
    private List<String> species;
    private String producer;
}