package io.activise.philyra.javapoet;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import io.activise.philyra.api.lang.Entity;

public class EntityIndex {
	private Map<String, TypeName> predefinedTypeMap = new HashMap<>();
	private Map<String, TypeName> entityLookupMap = new HashMap<>();

	private Map<String, AnnotationSpec.Builder> annotationSpecs = new HashMap<>();

	{
		predefinedTypeMap.put("String", ClassName.get(String.class));
		predefinedTypeMap.put("Date", ClassName.get(LocalDate.class));
		predefinedTypeMap.put("DateTime", ClassName.get(LocalDateTime.class));
	}

	public void registerEntity(Entity entity) {
		var name = entity.getName();
		entityLookupMap.put(name, ClassName.get(entity.getDeclaringUnit().getTargetPackage(entity), name));
	}

	public void registerEntities(List<Entity> entities) {
		entities.forEach(this::registerEntity);
	}

	public Optional<TypeName> getTypeName(String name) {
		if (isPredfinedType(name)) {
			return Optional.of(predefinedTypeMap.get(name));
		} else {
			return Optional.ofNullable(entityLookupMap.get(name));
		}
	}

	public boolean isPredfinedType(String name) {
		return predefinedTypeMap.containsKey(name);
	}

	public void registerAnnotationSpec(String key, AnnotationSpec.Builder builder) {
		annotationSpecs.put(key, builder);
	}

	public Optional<AnnotationSpec.Builder> getAnnotationSpec(String key) {
		return Optional.ofNullable(annotationSpecs.get(key));
	}

	public void applyAnnotations(TypeSpec.Builder entityClass) {
		annotationSpecs.values().forEach(a -> entityClass.addAnnotation(a.build()));
	}

}