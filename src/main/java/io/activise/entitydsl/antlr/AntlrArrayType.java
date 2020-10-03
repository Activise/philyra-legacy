package io.activise.entitydsl.antlr;

import io.activise.entitydsl.antlr.EntityDslParser.ArrayTypeContext;
import io.activise.entitydsl.api.Type;

public class AntlrArrayType implements Type {
  private ArrayTypeContext context;

  public AntlrArrayType(ArrayTypeContext context) {
    this.context = context;
  }

  @Override
  public String getId() {
    return context.ID().getText();
  }

}
