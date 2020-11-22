package io.activise.philyra.javapoet.processor;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.FieldSpec;
import io.activise.philyra.javapoet.EntityIndex;
import io.activise.philyra.javapoet.GenerationContext;
import io.activise.philyra.philyra.EAttribute;
import io.activise.philyra.philyra.EEntity;

public class AttributeModifiersProcessor implements AttributeProcessor<FieldSpec.Builder> {
  @Override
  public void process(EntityIndex entityIndex, EEntity entity, EAttribute attribute, FieldSpec.Builder fieldSpec) {
    var modifiers = attribute.getModifiers();

    if (modifiers.isId() || modifiers.isPk()) {
      fieldSpec.addAnnotation(Id.class);
      fieldSpec.addAnnotation(generatedValue(GenerationType.IDENTITY));
    }

    var generationContext = entityIndex.getGenerationContext(entity);
    if (modifiers.isIndex()) {
      addTableIndex(generationContext, attribute);
    }
  }

  private AnnotationSpec generatedValue(GenerationType generationType) {
    var generatedValueAnnotation = AnnotationSpec.builder(GeneratedValue.class);
    return generatedValueAnnotation.addMember(
      "strategy", "$T.$L", GenerationType.class, generationType
    ).build();
  }

  private void addTableIndex(GenerationContext generationContext, EAttribute attribute) {
    var tableAnnotation = generationContext.getAnnotation("table");
    tableAnnotation.ifPresent(t -> {
      t.addMember("indexes", "$L", indexAnnotation(attribute));
    });
  }

  private AnnotationSpec indexAnnotation(EAttribute attribute) {
    var indexAnnotation = AnnotationSpec.builder(Index.class);
    return indexAnnotation.addMember(
      "columnList", "$S", attribute.getName()
    ).build();
  }

}
