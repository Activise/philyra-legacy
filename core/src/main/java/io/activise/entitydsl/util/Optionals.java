package io.activise.entitydsl.util;

import java.util.Optional;
import java.util.function.Supplier;

public class Optionals {
  private Optionals() {
  }

  public static <T> Optional<T> safeOf(Supplier<T> supplier) {
    try {
      T supplied = supplier.get();
      if (supplied != null) {
        return Optional.of(supplied);
      }
    } catch (Exception e) {
    }
    return Optional.empty();
  }

}
