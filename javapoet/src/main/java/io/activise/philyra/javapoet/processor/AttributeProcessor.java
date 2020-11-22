package io.activise.philyra.javapoet.processor;

import io.activise.philyra.javapoet.EntityIndex;
import io.activise.philyra.philyra.EAttribute;
import io.activise.philyra.philyra.EEntity;

public interface AttributeProcessor<T> {
  void process(EntityIndex index, EEntity entity, EAttribute attribute, T context);

}
