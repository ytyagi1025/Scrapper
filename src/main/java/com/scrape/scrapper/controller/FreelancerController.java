package com.scrape.scrapper.controller;

import com.scrape.scrapper.model.ScrapeObject;
import com.scrape.scrapper.service.FreelancerScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/freelancer")
public class FreelancerController {
    @Autowired
    private FreelancerScraperService scraperService;

    @GetMapping("/scrapeUser")
    public ResponseEntity<ScrapeObject> searchFreelancerJobs(@RequestParam String url) throws IOException {
        ScrapeObject scrapeObject = scraperService.scrapeProfile(url);
        return new ResponseEntity<>(scrapeObject, HttpStatus.OK);
    }
}
