package de.zuellich.meal_planner.algorithms;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 */
@Service
public class AmountParser {

    private static final List<String> UNICODE_FRACTIONS = Arrays.asList(new String[] { "½", "¼", "¾" });
    /**
     * Stores values for basic fractions like 1/4.
     */
    private static final Map<String, Float> fractionLookup;

    static {
        fractionLookup = new HashMap<>(4);
        fractionLookup.put("1/8", 0.125f);
        fractionLookup.put("1/4", 0.25f);
        fractionLookup.put("¼", 0.25f);
        fractionLookup.put("1/2", 0.5f);
        fractionLookup.put("½", 0.5f);
        fractionLookup.put("3/4", 0.75f);
        fractionLookup.put("¾", 0.75f);
    }

    /**
     * Convert the string value into a float value. Can resolve very basic fractions. If the fraction
     * is unknown it will return 0.
     *
     * @param raw The raw value. Might be "1" or "1/8".
     * @return Returns the converted value or in case of an unknown fraction, empty string or null 0.
     */
    public float parseAmount(String raw) {
        if (raw == null || raw.isEmpty()) {
            return 0;
        }

        if (raw.contains("/") || UNICODE_FRACTIONS.contains(raw)) {
            return resolveFraction(raw);
        } else {
            return Float.parseFloat(raw);
        }
    }

    /**
     * Resolve a fraction by looking it up in the static storage. Returns 0 if not found.
     *
     * @param raw The raw string like 1/8.
     * @return The value of the fraction or 0.
     */
    private float resolveFraction(String raw) {
        Float value = fractionLookup.get(raw);

        if (value == null) {
            return 0f;
        } else {
            return value;
        }
    }
}
