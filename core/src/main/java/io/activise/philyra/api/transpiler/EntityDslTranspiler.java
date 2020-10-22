package io.activise.philyra.api.transpiler;

import java.util.List;

import io.activise.philyra.api.lang.CompilationUnit;

public interface EntityDslTranspiler {
  List<TranspilationResult> transpile(CompilationUnit compilationUnit);

}
