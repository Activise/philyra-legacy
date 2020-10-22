package io.activise.entitydsl.api.lang;

import java.util.List;
import java.util.Optional;

import io.activise.entitydsl.util.Optionals;

public interface CompilationUnit {
  Optional<GlobalBase> getGlobalBase();

  List<Entity> getEntities();

  default String getTargetPackage(Entity entity) {
    return Optionals.safeOf(() -> getGlobalBase().get().getOptionByKey("package").get().getValue())
        .orElse("io.activise.example");
  }

}