package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.FixtureBasedTest;
import de.zuellich.meal_planner.datatypes.RecipeFormat;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */
public class RecipeParserFactoryTest extends FixtureBasedTest {

    private RecipeParserFactory getInstance() {
        SchemaOrgParser schemaOrgParser = mock(SchemaOrgParser.class);
        when(schemaOrgParser.getFormat()).thenReturn(RecipeFormat.SCHEMA_ORG);

        Set<FormatDetector> detectors = new HashSet<>(1);
        detectors.add(new SchemaOrgFormatDetector(schemaOrgParser));
        return new RecipeParserFactory(detectors);
    }

    @Test
    public void returnsSchemaOrgParser() {
        String source = getResource("/fixtures/ingredientScanner/simple-schema-org.html");
        RecipeParserFactory factory = getInstance();
        RecipeParser actualParser = factory.getParser(source);
        assertEquals(RecipeFormat.SCHEMA_ORG, actualParser.getFormat());
    }

    @Test
    public void returnsNullParser() {
        RecipeParserFactory factory = getInstance();
        RecipeParser actualParser = factory.getParser("");
        assertEquals(RecipeFormat.UNKNOWN, actualParser.getFormat());
    }

}