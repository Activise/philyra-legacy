package io.activise.philyra.api.lang;

import java.util.List;
import java.util.Optional;

public interface Entity {
  public static final String INDEX_ATTRIBUTE = "idx";

  CompilationUnit getDeclaringUnit();

  String getName();

  Optional<String> getTableName();

  List<Attribute> getAttributes();

}
