package de.zuellich.meal_planner.controller;

import de.zuellich.meal_planner.datatypes.Recipe;
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
    public Recipe parse(@RequestParam(value="url") String url) {
        Recipe recipe = new Recipe("Test", Collections.emptyList(), url);
        return recipe;
    }
}
