package io.activise.philyra.javapoet;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import io.activise.philyra.philyra.EAttribute;
import io.activise.philyra.philyra.EEntity;
import io.activise.philyra.util.AbstractTypeInfo;

public class EntityIndex {
  private final Map<EEntity, GenerationContext> generationContexts = new HashMap<>();

  public Optional<TypeName> getTypeName(EAttribute attribute) {
    var typeInfo = AbstractTypeInfo.from(attribute.getType());
    return Optional.of(ClassName.get(typeInfo.getPackageName(), typeInfo.getSimpleName()));
  }

  public GenerationContext getGenerationContext(EEntity entity) {
    return generationContexts.computeIfAbsent(entity, k -> new GenerationContext());
  }
  
}
