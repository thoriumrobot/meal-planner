package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.datatypes.Ingredient;
import de.zuellich.meal_planner.datatypes.IngredientUnit;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Returns a list of ingredients based on XML.
 */
@Service
public class SchemaOrgIngredientScanner implements IngredientScanner {

    private final IngredientUnitLookup ingredientUnitLookup;
    private final AmountParser amountParser;

    /**
     * Create a new instance. Will only parse the source upon calling get.
     *
     * @param amountParser         Used to parse amounts for the ingredients.
     * @param ingredientUnitLookup Used to lookup the units for an ingredient.
     */
    public SchemaOrgIngredientScanner(AmountParser amountParser, IngredientUnitLookup ingredientUnitLookup) {
        this.amountParser = amountParser;
        this.ingredientUnitLookup = ingredientUnitLookup;
    }

    @Override
    public List<Ingredient> parse(String source) {
        Document document = Jsoup.parse(source);
        Elements ingredients = document.getElementsByAttributeValue("itemprop", "recipeIngredient");

        List<Ingredient> result = new ArrayList<>(ingredients.size());
        for (Element ingredient : ingredients) {
            result.add(parseIngredient(ingredient));
        }

        return result;
    }

    private Ingredient parseIngredient(Element ingredient) {
        Elements typeElement = ingredient.select(".wprm-recipe-ingredient-name");
        Elements amountElement = ingredient.select(".wprm-recipe-ingredient-amount");
        Elements ingredientElement = ingredient.select(".wprm-recipe-ingredient-unit");

        String type = typeElement.text();
        float amount = amountParser.parseAmount(amountElement.text());
        IngredientUnit ingredientUnit = ingredientUnitLookup.lookup(ingredientElement.text());

        return new Ingredient(type, amount, ingredientUnit);
    }

}
