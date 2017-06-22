package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.FixtureBasedTest;
import de.zuellich.meal_planner.datatypes.Recipe;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class SchemaOrgRecipeScannerTest extends FixtureBasedTest {

    @Test
    public void testCanRetrieveBasicInformationFromRecipe() {
        String fixture = getResource("/fixtures/ingredientScanner/simple-schema-org.html");
        RecipeScanner instance = new SchemaOrgRecipeScanner(fixture);
        Recipe recipe = instance.getRecipe();

        assertEquals("The scanner returns the name of the recipe.", "Quick Teriyaki Chicken Rice Bowls", recipe.getName());
        assertTrue("The scanner does not provide the ingredients.", recipe.getIngredients().isEmpty());
    }

    /**
     * Test that the recipe scanner returns the authors URL if the recipe itself does not carry the itemprop url.
     */
    @Test
    public void testReturnEmptyURLIfNotIncludedInSite() {
        String fixture = getResource("/fixtures/ingredientScanner/simple-schema-org.html");
        RecipeScanner instance = new SchemaOrgRecipeScanner(fixture);
        Recipe recipe = instance.getRecipe();

        assertEquals("The scanner provides an empty string as URL.","", recipe.getSource());
    }

}