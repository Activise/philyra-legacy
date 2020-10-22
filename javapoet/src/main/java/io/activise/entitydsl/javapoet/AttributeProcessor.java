package io.activise.entitydsl.javapoet;

import io.activise.entitydsl.api.lang.Attribute;
import io.activise.entitydsl.api.lang.Entity;

public interface AttributeProcessor<T> {
  void process(EntityIndex index, Entity entity, Attribute attribute, T context);

}
