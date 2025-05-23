package com.scrape.scrapper.model;

import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class ScrapeObject {
    private List<String> projectsCompleted;
    private String rating;
    private Set<String> portfolio;
    private String recommendations;
    private String earning;
    private Set<String> skills;
    private String specialisation;
}
