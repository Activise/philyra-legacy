package io.activise.philyra;

import io.activise.philyra.api.lang.CompilationUnit;

public interface EntityDslParser {
  CompilationUnit parse(String source);

}