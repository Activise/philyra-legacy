package io.activise.entitydsl.javapoet;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.FieldSpec.Builder;

import io.activise.entitydsl.api.lang.ArrayType;
import io.activise.entitydsl.api.lang.AssociationType;
import io.activise.entitydsl.api.lang.Attribute;
import io.activise.entitydsl.api.lang.Entity;

public class AssociationProcessor implements AttributeProcessor<FieldSpec.Builder> {
  @Override
  public void process(EntityIndex index, Entity entity, Attribute attribute, Builder context) {
    if (AssociationType.BI == attribute.getAssociationType()) {
      AnnotationSpec.Builder annotationSpec = null;
      if (isArrayAttribute(attribute)) {
        


      } else {
      }
    }
  }

  private boolean isArrayAttribute(Attribute attribute) {
    return attribute.getType() instanceof ArrayType;
  }

}
