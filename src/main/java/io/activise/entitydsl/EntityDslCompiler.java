package io.activise.entitydsl;

import io.activise.entitydsl.api.CompilationUnit;

public interface EntityDslCompiler {
    CompilationUnit compile(String source);

}