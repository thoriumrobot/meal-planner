package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.datatypes.Recipe;

/**
 *
 */
public interface RecipeParser {

    /**
     * @param source The source format to parse.
     * @return The parsed recipe instance.
     */
    Recipe parse(String source);

}
