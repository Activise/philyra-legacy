package io.activise.entitydsl.antlr;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import io.activise.entitydsl.antlr.EntityDslParser.EntityContext;
import io.activise.entitydsl.antlr.EntityDslParser.MemberContext;
import io.activise.entitydsl.api.Attribute;
import io.activise.entitydsl.api.Entity;
import io.activise.entitydsl.util.Optionals;

public class AntlrEntity implements Entity {
  private EntityContext context;

  public AntlrEntity(EntityContext context) {
    this.context = context;
  }

  @Override
  public String getName() {
    return context.ID().getText();
  }

  @Override
  public Optional<String> getTableName() {
    return Optionals.safeOf(() -> context.tableName().ID().getText());
  }

// @formatter:off
  @Override
  public List<Attribute> getAttributes() {
    var memberContexts = context.entityBody().member();
    return memberContexts.stream()
        .filter(m -> m.attribute() != null)
        .map(MemberContext::attribute)
        .map(AntlrAttribute::new)
        .collect(Collectors.toList());
  }
// @formatter:on

}
