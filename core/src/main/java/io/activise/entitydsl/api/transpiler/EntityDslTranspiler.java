package io.activise.entitydsl.api.transpiler;

import java.util.List;

import io.activise.entitydsl.api.lang.CompilationUnit;

public interface EntityDslTranspiler {
  List<TranspilationResult> transpile(CompilationUnit compilationUnit);

}
