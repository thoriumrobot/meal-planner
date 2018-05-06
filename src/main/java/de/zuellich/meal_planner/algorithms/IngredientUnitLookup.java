package de.zuellich.meal_planner.algorithms;

import de.zuellich.meal_planner.datatypes.IngredientUnit;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;

/** */
@Service
public class IngredientUnitLookup {

  private static IngredientUnitLookup instance;

  private Map<String, IngredientUnit> byShorthand;
  private Map<String, IngredientUnit> byPlural;
  private Map<String, IngredientUnit> bySingular;

  /** Create a new instance and setup the lookup table. */
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

  /** Setup the maps with their values. */
  private void setupLookupTable() {
    byShorthand = new HashMap<>(IngredientUnit.values().length);
    byPlural = new HashMap<>(IngredientUnit.values().length);
    bySingular = new HashMap<>(IngredientUnit.values().length);

    for (IngredientUnit unit : IngredientUnit.values()) {
      byShorthand.put(unit.getShorthand(), unit);
      byPlural.put(unit.getPlural(), unit);
      bySingular.put(unit.getSingular(), unit);
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
   *
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
   *
   * @param search The string to search. Can be shorthand or plural.
   * @return IngredientUnit.NULL if not found.
   */
  public IngredientUnit lookup(String search) {
    IngredientUnit result = byShorthand(search);
    if (!result.equals(IngredientUnit.NULL)) {
      return result;
    }

    result = byPlural(search);
    if (!result.equals(IngredientUnit.NULL)) {
      return result;
    } else {
      return bySingular(search);
    }
  }

  /**
   * Find an ingredient unit by its singular representation.
   *
   * @param search The search string that should be the singular representation of the unit you look
   *     for.
   * @return IngredientUnit.NULL if none found.
   */
  public IngredientUnit bySingular(String search) {
    IngredientUnit result = bySingular.get(search);

    if (result == null) {
      result = IngredientUnit.NULL;
    }

    return result;
  }
}
