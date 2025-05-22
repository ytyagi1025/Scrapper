package com.scrape.scrapper.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scrape.scrapper.model.ScrapeObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class FreelancerScraperService {

    public ScrapeObject scrapeProfile(String url) throws IOException {
        Document doc = Jsoup.connect(url)
                .userAgent("Mozilla/5.0") // simulate a browser
                .get();

        return parseDataFromHtml(doc);
    }

    public ScrapeObject parseDataFromHtml(Document doc) {
        ScrapeObject scrapeObject = new ScrapeObject();
        // Projects
        Elements projects = doc.select("fl-review-card");
        List<String> jobTitles = new ArrayList<>();
        for (Element project : projects) {
            String title = project.select("fl-review-card > div > div > div > fl-text:first-of-type > div > fl-link > a").text();
            jobTitles.add(title);
        }
        scrapeObject.setProjectsCompleted(jobTitles);

        // Rating
        Element rating = doc.select("div.Review-header-rating-text span").first();
        if (rating != null) {
            scrapeObject.setRating(rating.text());
        }
        else{
            scrapeObject.setRating("Unknown");
        }

        //Recommendation
        Element recommendation = doc.select("fl-review-card > div > div > div > fl-text:nth-of-type(2) > div").first();
        if (recommendation != null) {
            scrapeObject.setRecommendations(recommendation.text());
        }
        else{
            scrapeObject.setRecommendations("Unknown");
        }


        return scrapeObject;
    }
}
