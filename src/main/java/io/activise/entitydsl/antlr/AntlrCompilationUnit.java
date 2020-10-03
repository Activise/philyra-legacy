package io.activise.entitydsl.antlr;

import java.util.List;
import java.util.stream.Collectors;

import io.activise.entitydsl.antlr.EntityDslParser.CompilationUnitContext;
import io.activise.entitydsl.api.CompilationUnit;
import io.activise.entitydsl.api.Entity;

public class AntlrCompilationUnit implements CompilationUnit {
  private CompilationUnitContext context;

  public AntlrCompilationUnit(CompilationUnitContext context) {
    this.context = context;
  }

// @formatter:off
  @Override
  public List<Entity> getEntities() {
    var entityContexts = context.entity();
    return entityContexts.stream()
        .map(AntlrEntity::new)
        .collect(Collectors.toList());
  }
// @formatter:on

}
