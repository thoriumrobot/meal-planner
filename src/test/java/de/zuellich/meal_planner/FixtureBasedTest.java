package de.zuellich.meal_planner;

import java.io.*;
import java.util.Scanner;

/** A simple base class that allows us to easily parse our expectations. */
public class FixtureBasedTest {

  /**
   * Load a fixture from the resources.
   *
   * @param path Supply a path that can be resolved from the resources. Example
   *     "/folder-in-resources/file".
   * @return The parsed file or an empty string.
   */
  public String getResource(String path) {
    try (InputStream in = FixtureBasedTest.class.getResourceAsStream(path)) {
      Scanner scanner = new Scanner(in, "UTF-8").useDelimiter("\\A");
      return scanner.hasNext() ? scanner.next() : "";
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
