package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.datatypes.Recipe;
import org.springframework.stereotype.Service;

/**
 *
 */
@Service
public class RecipeService {

    private final RecipeParserFactory parserFactory;

    public RecipeService(RecipeParserFactory parserFactory) {
        this.parserFactory = parserFactory;
    }

    /**
     * Try to parse the given recipe.
     * @param source The recipe in its source format.
     * @return The parsed recipe instance.
     */
    public Recipe getRecipe(String source) {
        RecipeParser parser = parserFactory.getParser(source);
        return parser.parse(source);
    }
}
