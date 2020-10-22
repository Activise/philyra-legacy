package io.activise.entitydsl;

import io.activise.entitydsl.api.lang.CompilationUnit;

public interface EntityDslParser {
  CompilationUnit parse(String source);

}