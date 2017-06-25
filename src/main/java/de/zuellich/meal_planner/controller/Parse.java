package de.zuellich.meal_planner.controller;

import de.zuellich.meal_planner.algorithms.RecipeParser;
import de.zuellich.meal_planner.algorithms.RecipeParserFactory;
import de.zuellich.meal_planner.datatypes.Recipe;
import org.apache.commons.validator.routines.UrlValidator;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Collections;
import java.util.Scanner;

/**
 * Controller handles the endpoint for parsing raw recipes.
 */
@RestController
public class Parse {

    private final RecipeParserFactory factory;

    @Autowired
    public Parse(RecipeParserFactory factory) {
        this.factory = factory;
    }

    @RequestMapping("/parse")
    public ResponseEntity<Recipe> parse(@RequestParam(value="url") String url) {
        UrlValidator urlValidator = new UrlValidator(new String[] {"http", "https"});
        if (!urlValidator.isValid(url)) {
            return ResponseEntity.badRequest().build();
        }

        System.out.println(url);
        String sourceData = downloadSourceData(url);
        if (sourceData.isEmpty()) {
            return ResponseEntity.status(500).build();
        }

        RecipeParser parser = factory.getParser(sourceData);
        Recipe recipe = parser.parse(sourceData);
        return ResponseEntity.ok(recipe);
    }

    /**
     * Download the source data from the URL.
     * @param url The url that points to the source.
     * @return The source data or an empty string.
     */
    private String downloadSourceData(String url) {
        /*
         * TODO: Improve this so we do not parse the same html twice. Does it have any advantages over the way with javas
         * builtins (follows redirects etc...)?
          */
        try {
            return Jsoup.connect(url).get().html();
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }
}
