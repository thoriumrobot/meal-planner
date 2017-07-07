package de.zuellich.meal_planner.controller;

import de.zuellich.meal_planner.algorithms.RecipeFetcherService;
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

import java.io.IOException;

/**
 * Controller handles the endpoint for parsing raw recipes.
 */
@RestController
public class Parse {

    private final RecipeParserFactory factory;

    private final RecipeFetcherService fetcherService;

    @Autowired
    public Parse(RecipeParserFactory factory, RecipeFetcherService fetcherService) {
        this.factory = factory;
        this.fetcherService = fetcherService;
    }

    @RequestMapping("/parse")
    public ResponseEntity<Object> parse(@RequestParam(value = "url") String url) {
        UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        if (!urlValidator.isValid(url)) {
            return ResponseEntity.badRequest().build();
        }

        System.out.println(url);
        String sourceData = null;
        try {
            sourceData = downloadSourceData(url);
            if (sourceData.isEmpty()) {
                return ResponseEntity.status(500).build();
            }

            RecipeParser parser = factory.getParser(sourceData);
            Recipe recipe = parser.parse(sourceData);
            return ResponseEntity.ok(recipe);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    /**
     * Download the source data from the URL.
     *
     * @param url The url that points to the source.
     * @return The source data or an empty string.
     */
    private String downloadSourceData(String url) throws IOException {
        return fetcherService.fetchByURL(url);
    }
}
