package io.activise.entitydsl.api;

import java.util.List;

public interface Attribute {
  String getName();

  AssociationType getAssociationType();

  Type getType();

  List<AttributeOption> getAttributeOptions();

}
