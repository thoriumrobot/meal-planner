package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.datatypes.Ingredient;
import de.zuellich.meal_planner.datatypes.IngredientUnit;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class SchemaOrgIngredientScannerTest {

    @Test
    public void testCanGetIngredientsFromXML() throws IOException, URISyntaxException {
        String HTMLSource = getResource("/fixtures/ingredientScanner/simple-recipe.html");

        IngredientScanner scanner = new SchemaOrgIngredientScanner(HTMLSource, new AmountParser(), IngredientUnitLookup.getInstance());
        List<Ingredient> ingredientList = scanner.getIngredients();

        List<Ingredient> actualIngredients = getSampleIngredients();

        assertEquals(
                "Assert that the scanner reads the right ingredients.",
                actualIngredients,
                ingredientList);

    }

    private String getResource(String path) {
        InputStream in = getClass().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        Scanner scanner = new Scanner(in).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }

    private List<Ingredient> getSampleIngredients() {
        List<Ingredient> sampleIngredients = new ArrayList<>();
        sampleIngredients.add(new Ingredient("boneless", 1, IngredientUnit.LB));
        sampleIngredients.add(new Ingredient("salt and pepper", 0, IngredientUnit.NULL));
        sampleIngredients.add(new Ingredient("packed light brown sugar", 0.25f, IngredientUnit.CUP));
        sampleIngredients.add(new Ingredient("low-sodium soy sauce", 0.25f, IngredientUnit.CUP));
        sampleIngredients.add(new Ingredient("rice or apple cider vinegar", 2, IngredientUnit.TBSP));
        sampleIngredients.add(new Ingredient("ground ginger", 0.5f, IngredientUnit.TSP));
        sampleIngredients.add(new Ingredient("garlic", 2, IngredientUnit.CLOVES));
        sampleIngredients.add(new Ingredient("cornstarch", 1, IngredientUnit.TBSP));
        sampleIngredients.add(new Ingredient("rice", 0, IngredientUnit.NULL));
        sampleIngredients.add(new Ingredient("steamed broccoli", 0, IngredientUnit.NULL));
        return sampleIngredients;
    }
}