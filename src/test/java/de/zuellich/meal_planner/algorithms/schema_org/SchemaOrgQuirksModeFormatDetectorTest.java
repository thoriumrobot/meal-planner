package de.zuellich.meal_planner.algorithms.schema_org;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;

import de.zuellich.meal_planner.FixtureBasedTest;
import de.zuellich.meal_planner.algorithms.FormatDetector;
import org.junit.Test;

/** */
public class SchemaOrgQuirksModeFormatDetectorTest extends FixtureBasedTest {

  @Test
  public void recognizesQuirkySchemaOrgFormat() {
    SchemaOrgQuirksModeParser parser = mock(SchemaOrgQuirksModeParser.class);
    FormatDetector formatDetector = new SchemaOrgQuirksModeFormatDetector(parser);

    String recipeSource = getResource("/fixtures/ingredientScanner/recipes/schema-org-03.html");
    boolean isSupported = formatDetector.isSupported(recipeSource);
    assertTrue("Does recognize quirky schema.org.", isSupported);

    recipeSource = getResource("/fixtures/ingredientScanner/recipes/schema-org-01.html");
    isSupported = formatDetector.isSupported(recipeSource);
    assertFalse("Should not recognize standard schema.org as quirky.", isSupported);
  }
}
