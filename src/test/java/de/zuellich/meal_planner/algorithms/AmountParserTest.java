package de.zuellich.meal_planner.algorithms;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 *
 */
public class AmountParserTest {

    private AmountParser instance;

    @Before
    public void setUp() {
        instance = new AmountParser();
    }

    @Test
    public void testCanParseNormalNumber() {
        assertEquals(1f, instance.parseAmount("1"), 0);
        assertEquals(2f, instance.parseAmount("2"), 0);
    }

    @Test
    public void testCanHandleNegativeNumbers() {
        assertEquals(-1f, instance.parseAmount("-1"), 0);
        assertEquals(-10f, instance.parseAmount("-10"), 0);
    }

    @Test
    public void testCanHandleBasicFractions() {
        assertEquals(0.125f, instance.parseAmount("1/8"), 0);
        assertEquals(0.25f, instance.parseAmount("1/4"), 0);
        assertEquals(0.5, instance.parseAmount("1/2"), 0);
        assertEquals(0.75f, instance.parseAmount("3/4"), 0);
    }

    @Test
    public void testReturnsZeroIfEmptyStringOrNull() {
        assertEquals(0f, instance.parseAmount(null), 0);
        assertEquals(0f, instance.parseAmount(""), 0);
    }
}