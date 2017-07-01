package de.zuellich.meal_planner.algorithms.schema_org;

import de.zuellich.meal_planner.algorithms.AmountParser;
import de.zuellich.meal_planner.algorithms.IngredientUnitLookup;
import de.zuellich.meal_planner.algorithms.SchemaOrgIngredientScanner;
import de.zuellich.meal_planner.datatypes.Ingredient;
import de.zuellich.meal_planner.datatypes.IngredientUnit;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This IngredientScanner implements a specific quirk found on some websites. Instead of itemprop="recipeIngredient"
 * they use itemprop="ingredients".
 */
@Service
public class SchemaOrgQuirksModeIngredientScanner extends SchemaOrgIngredientScanner {

    /**
     * A regular expression capable of parsing most ingredient descriptions so that amount, unit and name of the
     * ingredient can be extracted. The first group also matches simple fractions.
     */
    private static final String INGREDIENT_REGEX = "([\\/\\d\\s]+)\\s?(\\w+)(.*)";
    private static final Pattern INGREDIENT_PATTERN = Pattern.compile(INGREDIENT_REGEX);

    /**
     * Create a new instance.
     *
     * @param amountParser         Used to parse amounts for the ingredients.
     * @param ingredientUnitLookup Used to lookup the units for an ingredient.
     */
    public SchemaOrgQuirksModeIngredientScanner(AmountParser amountParser, IngredientUnitLookup ingredientUnitLookup) {
        super(amountParser, ingredientUnitLookup);
    }

    @Override
    protected Elements getIngredientElements(Document document) {
        return document.getElementsByAttributeValue("itemprop", "ingredients");
    }

    @Override
    protected Ingredient parseIngredient(Element ingredient) {
        String text = ingredient.text();

        Matcher matcher = INGREDIENT_PATTERN.matcher(text);
        matcher.find();

        String rawAmount = matcher.group(1).trim();
        String rawUnit = matcher.group(2).trim();
        String rawName = matcher.group(3).trim();

        String name = rawName;
        float amount = getAmountParser().parseAmount(rawAmount);
        IngredientUnit unit = getIngredientUnitLookup().lookup(rawUnit);

        return new Ingredient(name, amount, unit);
    }
}
