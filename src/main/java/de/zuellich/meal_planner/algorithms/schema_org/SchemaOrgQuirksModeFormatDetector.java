package de.zuellich.meal_planner.algorithms.schema_org;

import de.zuellich.meal_planner.algorithms.FormatDetector;
import de.zuellich.meal_planner.algorithms.RecipeParser;
import de.zuellich.meal_planner.algorithms.SchemaOrgParser;
import de.zuellich.meal_planner.datatypes.RecipeFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 */
public class SchemaOrgQuirksModeFormatDetector implements FormatDetector {

    private final SchemaOrgParser parser;

    public SchemaOrgQuirksModeFormatDetector(SchemaOrgParser parser) {
        this.parser = parser;
    }

    @Override
    public boolean isSupported(String source) {
        Document document = Jsoup.parse(source);
        Elements recipeElement = document.getElementsByAttributeValue("itemtype", "http://schema.org/Recipe");
        Elements ingredientsElements = document.getElementsByAttributeValue("itemprop", "ingredients");
        return !recipeElement.isEmpty() && !ingredientsElements.isEmpty();
    }

    @Override
    public RecipeFormat getFormat() {
        return RecipeFormat.SCHEMA_ORG;
    }

    @Override
    public RecipeParser getParserInstance() {
        return parser;
    }
}
