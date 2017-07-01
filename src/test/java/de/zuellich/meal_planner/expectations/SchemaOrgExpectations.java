package de.zuellich.meal_planner.expectations;

import de.zuellich.meal_planner.datatypes.Ingredient;
import de.zuellich.meal_planner.datatypes.IngredientUnit;
import de.zuellich.meal_planner.datatypes.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that allows us to store the expectations (Recipe instances) for the SchemaOrg parser tests. This enables
 * us to use a parametrized test case.
 */
public class SchemaOrgExpectations {

    /**
     * Return the expected recipe instance for the first recipe.
     * @return The expected instance representing a recipe.
     */
    public static Recipe getSchemaOrg01() {
        String name = "Quick Teriyaki Chicken Rice Bowls";
        String url = "";
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient("boneless", 1, IngredientUnit.LB));
        ingredientList.add(new Ingredient("salt and pepper", 0, IngredientUnit.NULL));
        ingredientList.add(new Ingredient("packed light brown sugar", 0.25f, IngredientUnit.CUP));
        ingredientList.add(new Ingredient("low-sodium soy sauce", 0.25f, IngredientUnit.CUP));
        ingredientList.add(new Ingredient("rice or apple cider vinegar", 2, IngredientUnit.TBSP));
        ingredientList.add(new Ingredient("ground ginger", 0.5f, IngredientUnit.TSP));
        ingredientList.add(new Ingredient("garlic", 2, IngredientUnit.CLOVE));
        ingredientList.add(new Ingredient("cornstarch", 1, IngredientUnit.TBSP));
        ingredientList.add(new Ingredient("rice", 0, IngredientUnit.NULL));
        ingredientList.add(new Ingredient("steamed broccoli", 0, IngredientUnit.NULL));

        return new Recipe(name, ingredientList, url);
    }

    public static Recipe getSchemaOrg02() {
        String name = "Best Chimichurri Sauce";
        String url = "";
        List<Ingredient> ingredientList = new ArrayList<>();
        ingredientList.add(new Ingredient("flat leaf parsley", 1, IngredientUnit.BUNCH));
        ingredientList.add(new Ingredient("cilantro (optional for the South American Version)", 0.5f, IngredientUnit.BUNCH));
        ingredientList.add(new Ingredient("extra virgin olive oil", 0f, IngredientUnit.NULL));
        ingredientList.add(new Ingredient("large lime or lemon - juiced", 1, IngredientUnit.NULL));
        ingredientList.add(new Ingredient("garlic", 4, IngredientUnit.NULL));
        ingredientList.add(new Ingredient("sea salt + more to taste", 0.5f, IngredientUnit.TSP));
        ingredientList.add(new Ingredient("chile pepper or a pinch of red pepper flakes", 0.5f, IngredientUnit.NULL));
        ingredientList.add(new Ingredient("fresh oregano leaves", 2, IngredientUnit.TBSP));

        return new Recipe(name, ingredientList, url);
    }
}
