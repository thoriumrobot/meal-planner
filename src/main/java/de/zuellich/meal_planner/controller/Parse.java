package de.zuellich.meal_planner.controller;

import de.zuellich.meal_planner.algorithms.RecipeFetcherService;
import de.zuellich.meal_planner.algorithms.RecipeParser;
import de.zuellich.meal_planner.algorithms.RecipeService;
import de.zuellich.meal_planner.datatypes.Recipe;
import org.apache.commons.validator.routines.UrlValidator;
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

    private final RecipeService recipeService;

    private final RecipeFetcherService fetcherService;

    @Autowired
    public Parse(RecipeService recipeService, RecipeFetcherService fetcherService) {
        this.recipeService = recipeService;
        this.fetcherService = fetcherService;
    }

    @RequestMapping("/parse")
    public ResponseEntity<Object> parse(@RequestParam(value = "url") String url) {
        ResponseEntity<Object> response;

        UrlValidator urlValidator = new UrlValidator(new String[]{"http", "https"});
        if (!urlValidator.isValid(url)) {
            return ResponseEntity.badRequest().build();
        }

        try {
            response = fetchRecipeFromURL(url);
        } catch (IOException e) {
            response = ResponseEntity.status(500).body(e.getMessage());
        }

        return response;
    }

    /**
     * Try to fetch the recipe from the URL and create a response.
     * @param url The URL to fetch the recipe from.
     * @return A response that either is the recipe or an error.
     * @throws IOException In case something goes wrong downloading the sources.
     */
    private ResponseEntity<Object> fetchRecipeFromURL(String url) throws IOException {
        ResponseEntity<Object> response;
        String recipeSource = fetcherService.fetchByURL(url);

        if (recipeSource.isEmpty()) {
            response = ResponseEntity.status(500).body("Empty response.");
        } else {
            Recipe recipe = recipeService.getRecipe(recipeSource);
            response = ResponseEntity.ok(recipe);
        }

        return response;
    }
}
