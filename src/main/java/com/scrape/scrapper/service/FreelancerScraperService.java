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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

        //Specialization
        Element specialization = doc.select("app-user-profile-summary-description-redesign div").first();
        if (specialization != null) {
            scrapeObject.setSpecialisation(specialization.text());
        }
        else{
            scrapeObject.setSpecialisation("Unknown");
        }

        //Earning Score
        Element earningScore = doc.select("div.EarningsContainer p").first();
        if (earningScore != null) {
            scrapeObject.setEarning(earningScore.text());
        }
        else{
            scrapeObject.setEarning("Unknown");
        }

        //skills
        Elements specialisations = doc.select("fl-review-card fl-tag");
        Set<String> specialisationSet = new HashSet<>();
        for (Element speElement : specialisations) {
            String title = speElement.text();
            specialisationSet.add(title);
        }
        scrapeObject.setSkills(specialisationSet);

        //portfolio
        Elements portfolioItems = doc.select("app-portfolio-item-card-redesign fl-carousel-item fl-text");
        Set<String> portfolioSet = new HashSet<>();
        for (Element speElement : portfolioItems) {
            String title = speElement.text();
            portfolioSet.add(title);
        }
        scrapeObject.setPortfolio(portfolioSet);


        return scrapeObject;
    }
}
