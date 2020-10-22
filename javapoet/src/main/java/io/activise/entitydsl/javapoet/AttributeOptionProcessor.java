package io.activise.entitydsl.javapoet;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;

import io.activise.entitydsl.api.lang.Attribute;
import io.activise.entitydsl.api.lang.Entity;

public class AttributeOptionProcessor implements AttributeProcessor<FieldSpec.Builder> {
  @Override
  public void process(EntityIndex index, Entity entity, Attribute attribute, FieldSpec.Builder fieldSpec) {
    if (attribute.hasOption("pk")) {
      fieldSpec.addAnnotation(Id.class);
      fieldSpec.addAnnotation(generatedValue(GenerationType.IDENTITY));
    }
    if (attribute.hasOption("idx")) {
      addTableIndex(index, attribute);
    }
  }

  private AnnotationSpec generatedValue(GenerationType generationType) {
    return AnnotationSpec.builder(GeneratedValue.class)
        .addMember("strategy", "$T.$L", GenerationType.class, GenerationType.IDENTITY).build();
  }

  private AnnotationSpec indexAnnotation(Attribute attribute) {
    var indexAnnotation = AnnotationSpec.builder(Index.class);
    indexAnnotation.addMember("columnList", "$S", attribute.getName());
    return indexAnnotation.build();
  }

  private void addTableIndex(EntityIndex entityIndex, Attribute attribute) {
    var tableAnnotation = entityIndex.getAnnotationSpec("table");
    tableAnnotation.ifPresent(t -> {
      t.addMember("indexes", "$L", indexAnnotation(attribute));
    });
  }

}
