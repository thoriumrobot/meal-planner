package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.FixtureBasedTest;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test that we can detect recipes that are annotated in Schema.org style.
 */
public class SchemaOrgFormatDetectorTest extends FixtureBasedTest {

    @Test
    public void testCanDetectSchemaOrg() {
        String source = getResource("/fixtures/ingredientScanner/simple-schema-org.html");
        FormatDetector detector = new SchemaOrgFormatDetector(source);
        boolean isSchemaOrgFormatted = detector.isDetected();

        assertTrue(isSchemaOrgFormatted);
    }

}