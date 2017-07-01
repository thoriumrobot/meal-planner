package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.FixtureBasedTest;
import de.zuellich.meal_planner.datatypes.Recipe;
import de.zuellich.meal_planner.expectations.SchemaOrgExpectations;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;

/**
 *
 */
@RunWith(Parameterized.class)
public class SchemaOrgParserTest extends FixtureBasedTest {

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {"/fixtures/ingredientScanner/recipes/schema-org-01.html", SchemaOrgExpectations.getSchemaOrg01() }
        });
    }

    /**
     * Parameter with the path to the recipe to load.
     */
    @Parameterized.Parameter
    public String inputRecipePath;

    /**
     * Parameter with a configured instance of Recipe that should match.
     */
    @Parameterized.Parameter(1)
    public Recipe expectedRecipe;

    @Test
    public void testCanReturnAProperRecipeInstance() {
        String source = getResource(inputRecipePath);

        SchemaOrgRecipeScanner recipeScanner = new SchemaOrgRecipeScanner();
        SchemaOrgIngredientScanner ingredientScanner = new SchemaOrgIngredientScanner(
                new AmountParser(),
                IngredientUnitLookup.getInstance()
        );
        RecipeParser parser = new SchemaOrgParser(recipeScanner, ingredientScanner);
        Recipe recipe = parser.parse(source);

        assertEquals(expectedRecipe, recipe);
    }

}
