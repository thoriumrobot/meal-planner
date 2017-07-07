package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.FixtureBasedTest;
import de.zuellich.meal_planner.algorithms.schema_org.SchemaOrgParser;
import de.zuellich.meal_planner.datatypes.Recipe;
import de.zuellich.meal_planner.expectations.SchemaOrgExpectations;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 */
public class RecipeServiceTest extends FixtureBasedTest {

    @Test
    public void returnsRecipeForSource() {
        Recipe expected = SchemaOrgExpectations.getSchemaOrg01();
        RecipeParserFactory parserFactory = getMockedParserFactory(expected);

        String source = getResource("/fixtures/ingredientScanner/recipes/schema-org-01.html");
        RecipeService service = new RecipeService(parserFactory);
        Recipe result = service.getRecipe(source);

        assertEquals(expected, result);
    }

    /**
     * Get a mocked parser factory that resolves the expected recipe.
     * @param expected
     * @return
     */
    private RecipeParserFactory getMockedParserFactory(Recipe expected) {
        RecipeParser schemaOrgParser = mock(SchemaOrgParser.class);
        when(schemaOrgParser.parse(anyString())).thenReturn(expected);

        RecipeParserFactory parserFactory = mock(RecipeParserFactory.class);
        when(parserFactory.getParser(anyString())).thenReturn(schemaOrgParser);

        return parserFactory;
    }

}