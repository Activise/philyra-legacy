package io.activise.philyra.javapoet.utils;

public class StringUtils {
  private StringUtils() {
  }
  
  public static String capitalize(String input) {
    return input.substring(0, 1).toUpperCase() + input.substring(1);
  }
  
}
