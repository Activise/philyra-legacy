package io.activise.entitydsl.antlr;

import io.activise.entitydsl.antlr.EntityDslParser.AttributeOptionContext;
import io.activise.entitydsl.api.AttributeOption;

public class AntlrAttributeOption implements AttributeOption {
  private AttributeOptionContext context;

  public AntlrAttributeOption(AttributeOptionContext context) {
    this.context = context;
  }

  @Override
  public String getName() {
    return context.ID().getText();
  }

}
