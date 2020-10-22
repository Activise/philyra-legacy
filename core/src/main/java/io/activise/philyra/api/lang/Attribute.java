package io.activise.philyra.api.lang;

import java.util.List;

public interface Attribute {
  String getName();

  AssociationType getAssociationType();

  Type getType();

  List<AttributeOption> getAttributeOptions();

  default boolean hasOption(String key) {
    return getAttributeOptions().stream().anyMatch(a -> a.getName().equalsIgnoreCase(key));
  }

}
