package io.activise.philyra.javapoet;

import io.activise.philyra.api.lang.Attribute;
import io.activise.philyra.api.lang.Entity;

public interface AttributeProcessor<T> {
  void process(EntityIndex index, Entity entity, Attribute attribute, T context);

}
