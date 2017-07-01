package de.zuellich.meal_planner.algorithms.schema_org;

import de.zuellich.meal_planner.FixtureBasedTest;
import de.zuellich.meal_planner.algorithms.FormatDetector;
import de.zuellich.meal_planner.algorithms.SchemaOrgParser;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 *
 */
public class SchemaOrgQuirksModeFormatDetectorTest extends FixtureBasedTest {

    @Test
    public void recognizesQuirkySchemaOrgFormat() {
        SchemaOrgParser parser = mock(SchemaOrgParser.class);
        FormatDetector formatDetector = new SchemaOrgQuirksModeFormatDetector(parser);

        String recipeSource = getResource("/fixtures/ingredientScanner/recipes/schema-org-03.html");
        boolean isSupported = formatDetector.isSupported(recipeSource);
        assertTrue("Does recognize quirky schema.org.", isSupported);

        recipeSource = getResource("/fixtures/ingredientScanner/recipes/schema-org-01.html");
        isSupported = formatDetector.isSupported(recipeSource);
        assertFalse("Should not recognize standard schema.org as quirky.", isSupported);
    }

}