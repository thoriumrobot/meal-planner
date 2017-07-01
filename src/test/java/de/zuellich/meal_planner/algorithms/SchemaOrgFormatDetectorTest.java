package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.FixtureBasedTest;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

/**
 * Test that we can detect recipes that are annotated in Schema.org style.
 */
public class SchemaOrgFormatDetectorTest extends FixtureBasedTest {

    @Test
    public void testCanDetectSchemaOrg() {
        SchemaOrgParser parser = mock(SchemaOrgParser.class);
        String source = getResource("/fixtures/ingredientScanner/recipes/schema-org-01.html");
        FormatDetector detector = new SchemaOrgFormatDetector(parser);
        boolean isSchemaOrgFormatted = detector.isSupported(source);

        assertTrue(isSchemaOrgFormatted);
    }

}