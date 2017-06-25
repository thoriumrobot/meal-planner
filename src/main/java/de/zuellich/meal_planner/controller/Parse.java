package de.zuellich.meal_planner.controller;

import de.zuellich.meal_planner.datatypes.Recipe;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

/**
 * Controller handles the endpoint for parsing raw recipes.
 */
@RestController
public class Parse {

    @RequestMapping("/parse")
    public ResponseEntity<Recipe> parse(@RequestParam(value="url") String url) {
        UrlValidator urlValidator = new UrlValidator(new String[] {"http", "https"});
        if (!urlValidator.isValid(url)) {
            return ResponseEntity.badRequest().build();
        }

        Recipe recipe = new Recipe("Test", Collections.emptyList(), url);
        return ResponseEntity.ok(recipe);
    }
}
