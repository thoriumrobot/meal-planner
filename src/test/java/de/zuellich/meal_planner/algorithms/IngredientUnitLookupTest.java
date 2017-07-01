package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.datatypes.IngredientUnit;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 *
 */
public class IngredientUnitLookupTest {

    private IngredientUnitLookup instance;

    @Before
    public void setUp() {
        instance = IngredientUnitLookup.getInstance();
    }

    @Test
    public void canLookupByUnitShorthand() {
        IngredientUnit cupUnit = instance.byShorthand(IngredientUnit.CUP.getShorthand());
        assertEquals(IngredientUnit.CUP, cupUnit);
    }

    @Test
    public void canLookupByUnitPlural() {
        IngredientUnit cloveUnit = instance.byPlural(IngredientUnit.CLOVES.getPlural());
        assertEquals(IngredientUnit.CLOVES, cloveUnit);
    }

    @Test
    public void canLookupChained() {
        IngredientUnit cloveUnit = instance.lookup(IngredientUnit.CLOVES.getShorthand());
        assertEquals("Search the shorthand for an ingredient", IngredientUnit.CLOVES, cloveUnit);

        cloveUnit = instance.lookup(IngredientUnit.CLOVES.getPlural());
        assertEquals("Search the plural for an ingredient", IngredientUnit.CLOVES, cloveUnit);
    }

    @Test
    public void returnNullIngredientUnitInsteadOfNullIfUnitNotRecognized() {
        IngredientUnit actual = instance.byShorthand("notAnUnitActually");
        assertEquals("Return NULL object when unit not recognized", IngredientUnit.NULL, actual);

        actual = instance.byPlural("notAnUnitActually");
        assertEquals("Return NULL object when unit plural not recognized", IngredientUnit.NULL, actual);
    }

}