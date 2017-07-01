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
    private Map<String, IngredientUnit> byPlural;

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
        byPlural = new HashMap<>(IngredientUnit.values().length);

        for (IngredientUnit unit : IngredientUnit.values()) {
            byShorthand.put(unit.getShorthand(), unit);
            byPlural.put(unit.getPlural(), unit);
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

    /**
     * Try to find the unit type by its plural form.
     * @param plural The string that supposedly is plural.
     * @return IngredientUnit.NULL if not found.
     */
    public IngredientUnit byPlural(String plural) {
        IngredientUnit result = byPlural.get(plural);

        if (result == null) {
            result = IngredientUnit.NULL;
        }

        return result;
    }

    /**
     * Try to find an unit by looking up search string in all search maps.
     * @param search The string to search. Can be shorthand or plural.
     * @return IngredientUnit.NULL if not found.
     */
    public IngredientUnit lookup(String search) {
        IngredientUnit result = byShorthand(search);

        if (result.equals(IngredientUnit.NULL)) {
            result = byPlural(search);
        }

        return result;
    }
}
