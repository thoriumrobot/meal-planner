package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.datatypes.IngredientUnit;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Service
public class IngredientUnitLookup {

    private static IngredientUnitLookup instance;

    private Map<String, IngredientUnit> byShorthand;

    /**
     * Create a new instance and setup the lookup table.
     */
    private IngredientUnitLookup() {
        setupLookupTable();
    }

    /**
     * Get an instance.
     *
     * @return The new instance.
     */
    public static IngredientUnitLookup getInstance() {
        if (instance == null) {
            instance = new IngredientUnitLookup();
        }

        return instance;
    }

    /**
     * Setup the maps with their values.
     */
    private void setupLookupTable() {
        byShorthand = new HashMap<>(IngredientUnit.values().length);

        for (IngredientUnit unit : IngredientUnit.values()) {
            byShorthand.put(unit.getShorthand(), unit);
        }
    }

    /**
     * Try to find the unit type by its shorthand.
     *
     * @param shorthand The shorthand to lookup.
     * @return IngredientUnit.NULL if not found.
     */
    public IngredientUnit byShorthand(String shorthand) {
        IngredientUnit result = byShorthand.get(shorthand);

        if (result == null) {
            result = IngredientUnit.NULL;
        }

        return result;
    }

}
