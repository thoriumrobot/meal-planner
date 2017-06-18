package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.datatypes.RecipeFormat;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 *
 */
public class SchemaOrgFormatDetector implements FormatDetector {

    private final String htmlSource;

    /**
     * Try to recognize the HTMLSource as using Schema.org formats.
     * @param HTMLSource The HTML source to parse a recipe from.
     */
    public SchemaOrgFormatDetector(String HTMLSource) {
        htmlSource = HTMLSource;
    }

    @Override
    public boolean isDetected() {
        Document document = Jsoup.parse(htmlSource);
        boolean isWprmRecipe = canFindWprmRecipe(document);
        boolean hasSchemaOrgAnnotation = canFindSchemaOrgAnnotation(document);
        return isWprmRecipe && hasSchemaOrgAnnotation;
    }

    /**
     * Try to find the schema.org recipe annotation.
     * @param document The recipes as jsoup document.
     * @return True if we found the schema.org recipe annotation.
     */
    private boolean canFindSchemaOrgAnnotation(Document document) {
        Elements elements = document.getElementsByAttributeValue("itemtype", "http://schema.org/Recipe");
        return !elements.isEmpty();
    }

    /**
     * Try to find hints for a wprm "annotated" recipe.
     * @param document The recipe as jsoup document.
     * @return True if we found an element with an wprm-recipe class.
     */
    private boolean canFindWprmRecipe(Document document) {
        Elements elements = document.getElementsByAttributeValueContaining("class", "wprm-recipe");
        return !elements.isEmpty();
    }

    @Override
    public RecipeFormat getFormat() {
        return RecipeFormat.SCHEMA_ORG;
    }

}
