package io.activise.entitydsl.antlr;

import io.activise.entitydsl.antlr.EntityDslParser.TypeContext;
import io.activise.entitydsl.api.Type;

public class AntlrType implements Type {
  private TypeContext context;

  public AntlrType(TypeContext context) {
    this.context = context;
  }

  @Override
  public String getId() {
    return context.ID().getText();
  }

}
