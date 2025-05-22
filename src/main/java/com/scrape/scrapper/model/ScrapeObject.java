package com.scrape.scrapper.model;

import lombok.Data;

import java.util.List;

@Data
public class ScrapeObject {
    private List<String> projectsCompleted;
    private String rating;
    private String portfolio;
    private String recommendations;
    private int earning;
    private String specialization;
    private String language;
}
