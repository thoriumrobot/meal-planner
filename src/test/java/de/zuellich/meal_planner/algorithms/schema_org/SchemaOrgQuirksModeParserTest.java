package de.zuellich.meal_planner.algorithms.schema_org;

import de.zuellich.meal_planner.FixtureBasedTest;
import de.zuellich.meal_planner.algorithms.AmountParser;
import de.zuellich.meal_planner.algorithms.IngredientUnitLookup;
import de.zuellich.meal_planner.algorithms.RecipeParser;
import de.zuellich.meal_planner.datatypes.Recipe;
import de.zuellich.meal_planner.expectations.SchemaOrgExpectations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 *
 */
@RunWith(Parameterized.class)
public class SchemaOrgQuirksModeParserTest extends FixtureBasedTest {

    /**
     * The base path to the recipe fixtures.
     */
    private static final String recipeFixtureBasePath = "/fixtures/ingredientScanner/recipes";

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {recipeFixtureBasePath + "/schema-org-03.html", SchemaOrgExpectations.getSchemaOrg03() },
        });
    }

    @Parameterized.Parameter
    public String recipeSourcePath;

    @Parameterized.Parameter(1)
    public Recipe expectedRecipe;


    @Test
    public void canParseQuirkySchemaOrgRecipes() {
        SchemaOrgRecipeScanner recipeScanner = new SchemaOrgRecipeScanner();
        SchemaOrgQuirksModeIngredientScanner ingredientScanner = new SchemaOrgQuirksModeIngredientScanner(
                new AmountParser(),
                IngredientUnitLookup.getInstance());

        RecipeParser parser = new SchemaOrgQuirksModeParser(recipeScanner, ingredientScanner);

        String recipeSource = getResource(recipeSourcePath);
        Recipe parsedRecipe = parser.parse(recipeSource);

        assertEquals(expectedRecipe, parsedRecipe);
    }

}