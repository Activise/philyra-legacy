package io.activise.entitydsl.api;

import java.util.List;
import java.util.Optional;

public interface Entity {
  String getName();

  Optional<String> getTableName();

  List<Attribute> getAttributes();

}
