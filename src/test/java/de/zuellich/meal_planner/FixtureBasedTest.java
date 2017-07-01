package de.zuellich.meal_planner;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Scanner;

/**
 * A simple base class that allows us to easily parse our expectations.
 */
public class FixtureBasedTest {

    /**
     * Load a fixture from the resources.
     * @param path Supply a path that can be resolved from the resources. Example "/folder-in-resources/file".
     * @return The parsed file or an empty string.
     */
    public String getResource(String path) {
        InputStream in = getClass().getResourceAsStream(path);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        Scanner scanner = new Scanner(in).useDelimiter("\\A");
        return scanner.hasNext() ? scanner.next() : "";
    }
}
