package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.FixtureBasedTest;
import de.zuellich.meal_planner.datatypes.Ingredient;
import de.zuellich.meal_planner.datatypes.IngredientUnit;
import de.zuellich.meal_planner.datatypes.Recipe;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class SchemaOrgParserTest extends FixtureBasedTest {

    @Test
    public void testCanReturnAProperRecipeInstance() {
        String source = getResource("/fixtures/ingredientScanner/simple-schema-org.html");

        SchemaOrgRecipeScanner recipeScanner = new SchemaOrgRecipeScanner();
        SchemaOrgIngredientScanner ingredientScanner = new SchemaOrgIngredientScanner(
                new AmountParser(),
                IngredientUnitLookup.getInstance()
        );
        RecipeParser parser = new SchemaOrgParser(recipeScanner, ingredientScanner);
        Recipe recipe = parser.parse(source);

        assertEquals(getExpected(), recipe);
    }

    /**
     * @return The expected instance representing a recipe.
     */
    private Recipe getExpected() {
        String name = "Quick Teriyaki Chicken Rice Bowls";
        String url = "";
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient("boneless", 1, IngredientUnit.LB));
        ingredientList.add(new Ingredient("salt and pepper", 0, IngredientUnit.NULL));
        ingredientList.add(new Ingredient("packed light brown sugar", 0.25f, IngredientUnit.CUP));
        ingredientList.add(new Ingredient("low-sodium soy sauce", 0.25f, IngredientUnit.CUP));
        ingredientList.add(new Ingredient("rice or apple cider vinegar", 2, IngredientUnit.TBSP));
        ingredientList.add(new Ingredient("ground ginger", 0.5f, IngredientUnit.TSP));
        ingredientList.add(new Ingredient("garlic", 2, IngredientUnit.CLOVES));
        ingredientList.add(new Ingredient("cornstarch", 1, IngredientUnit.TBSP));
        ingredientList.add(new Ingredient("rice", 0, IngredientUnit.NULL));
        ingredientList.add(new Ingredient("steamed broccoli", 0, IngredientUnit.NULL));

        return new Recipe(name, ingredientList, url);
    }
}
