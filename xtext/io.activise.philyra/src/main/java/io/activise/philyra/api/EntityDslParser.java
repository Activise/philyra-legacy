package io.activise.philyra.api;

import io.activise.philyra.philyra.ECompilationUnit;

public interface EntityDslParser {
  ECompilationUnit parse(String source);

}