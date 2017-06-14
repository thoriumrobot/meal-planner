package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.datatypes.IngredientUnit;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class IngredientUnitLookupTest {

    @Test
    public void testCanLookupByUnitShorthand() {
        IngredientUnitLookup instance = IngredientUnitLookup.getInstance();
        IngredientUnit cupUnit = instance.byShorthand(IngredientUnit.CUP.getShorthand());
        assertEquals(IngredientUnit.CUP, cupUnit);
    }

}