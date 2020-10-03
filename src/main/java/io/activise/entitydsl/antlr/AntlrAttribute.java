package io.activise.entitydsl.antlr;

import java.util.List;
import java.util.stream.Collectors;

import io.activise.entitydsl.antlr.EntityDslParser.AttributeContext;
import io.activise.entitydsl.api.AssociationType;
import io.activise.entitydsl.api.Attribute;
import io.activise.entitydsl.api.AttributeOption;
import io.activise.entitydsl.api.Type;

public class AntlrAttribute implements Attribute {
  private AttributeContext context;

  public AntlrAttribute(AttributeContext context) {
    this.context = context;
  }

  @Override
  public String getName() {
    return context.ID().getText();
  }

  @Override
  public AssociationType getAssociationType() {
    var associationContext = context.associationType();
    if (associationContext.NORMAL() != null) {
      return AssociationType.NORMAL;
    } else if (associationContext.BI() != null) {
      return AssociationType.BI;
    } else if (associationContext.EMBEDDED() != null) {
      return AssociationType.EMBEDDED;
    }

    throw new RuntimeException("Failed to get association type from " + getName());
  }

  @Override
  public Type getType() {
    if (context.type() != null) {
      return new AntlrType(context.type());
    } else if (context.arrayType() != null) {
      return new AntlrArrayType(context.arrayType());
    }

    throw new RuntimeException("Failed to get type from " + getName());
  }

  @Override
  public List<AttributeOption> getAttributeOptions() {
    return context.attributeOption().stream()
            .map(AntlrAttributeOption::new)
            .collect(Collectors.toList());
  }

}
