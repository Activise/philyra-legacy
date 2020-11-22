package io.activise.philyra.javapoet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.TypeSpec;
import io.activise.philyra.philyra.EAttribute;

public class GenerationContext {
  private final Map<String, AnnotationSpec.Builder> annotationSpecs = new HashMap<>();
  private final Map<EAttribute, EAttribute> relationLinks = new HashMap<>();
  
  public void registerAnnotation(String key, AnnotationSpec.Builder annotation) {
    annotationSpecs.put(key, annotation);
  }

  public Optional<AnnotationSpec.Builder> getAnnotation(String key) {
    return Optional.ofNullable(annotationSpecs.get(key));
  }
  
  public void applyAnnotations(TypeSpec.Builder entityClass) {
    annotationSpecs.values().forEach(a -> entityClass.addAnnotation(a.build()));
  }
  
  public void addRelation(EAttribute attribute) {
    var opposite = attribute.getOpposite();
    if (opposite != null) {
      relationLinks.put(opposite, attribute);
    }
  }
  
  public Optional<EAttribute> getRelation(EAttribute attribute) {
    return Optional.ofNullable(relationLinks.get(attribute));
  }

}
