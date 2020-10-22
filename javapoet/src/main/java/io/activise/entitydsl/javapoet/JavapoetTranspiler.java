package io.activise.entitydsl.javapoet;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import io.activise.entitydsl.api.lang.ArrayType;
import io.activise.entitydsl.api.lang.CompilationUnit;
import io.activise.entitydsl.api.lang.Entity;
import io.activise.entitydsl.api.lang.Type;
import io.activise.entitydsl.api.transpiler.EntityDslTranspiler;
import io.activise.entitydsl.api.transpiler.TranspilationResult;

public class JavapoetTranspiler implements EntityDslTranspiler {
  private EntityIndex entityIndex = new EntityIndex();

  private final Set<AttributeProcessor<FieldSpec.Builder>> attributeProcessors = new HashSet<>(
      Arrays.asList(new AssociationProcessor(), new AttributeOptionProcessor()));

  @Override
  public List<TranspilationResult> transpile(CompilationUnit compilationUnit) {
    var result = new ArrayList<TranspilationResult>();

    entityIndex.registerEntities(compilationUnit.getEntities());

    for (var entity : compilationUnit.getEntities()) {
      var entityClass = TypeSpec.classBuilder(entity.getName()).addModifiers(Modifier.PUBLIC);

      prepareClassMeta(entity, entityClass);

      for (var attribute : entity.getAttributes()) {
        // TODO: Remove try 'n catch
        try {
          var typeId = attribute.getType();

          var className = determineTypeName(typeId);
          var fieldSpec = FieldSpec.builder(className, attribute.getName(), Modifier.PRIVATE);

          attributeProcessors.forEach(p -> p.process(entityIndex, entity, attribute, fieldSpec));

          entityClass.addField(fieldSpec.build());
          entityClass.addMethod(createGetter(className, attribute.getName()));
        } catch (Exception e) {
        }
      }

      entityIndex.applyAnnotations(entityClass);

      var transpilationResult = createTranspilationResult(compilationUnit, entity, entityClass.build());
      result.add(transpilationResult);
    }

    return result;
  }

// @formatter:off
  private void prepareClassMeta(Entity entity, TypeSpec.Builder entityClass) {
    entityClass.addAnnotation(javax.persistence.Entity.class);
    var tableAnnotation = AnnotationSpec.builder(Table.class)
        .addMember("name", "$S", determineTableName(entity));

    entityIndex.registerAnnotationSpec("table", tableAnnotation);
  }
// @formatter:on

  private String determineTableName(Entity entity) {
    return entity.getTableName().orElse(entity.getName());
  }

  private TypeName determineTypeName(Type type) {
    var typeName = entityIndex.getTypeName(type.getId()).orElse(null);
    if (typeName == null) {
      throw new RuntimeException("The type " + type.getId() + " is not supported.");
    }

    if (type instanceof ArrayType) {
      return ParameterizedTypeName.get(ClassName.get(List.class), typeName);
    } else {
      return typeName;
    }
  }

// @formatter:off
  private MethodSpec createGetter(TypeName typeName, String name) {
    return MethodSpec.methodBuilder("get" + capitalize(name))
        .addModifiers(Modifier.PUBLIC)
        .returns(typeName)
        .addStatement("return $N", name)
        .build();
  }
// @formatter:on

  private String capitalize(String input) {
    return input.substring(0, 1).toUpperCase() + input.substring(1);
  }

  private TranspilationResult createTranspilationResult(CompilationUnit compilationUnit, Entity entity, TypeSpec entityClass) {
    var packageName = compilationUnit.getTargetPackage(entity);
    var source = createJavaSource(packageName, entityClass);
    return new TranspilationResult(entity.getName(), packageName, source);
  }

  private String createJavaSource(String packageName, TypeSpec typeSpec) {
    var entityJavaFile = JavaFile.builder(packageName, typeSpec).build();
    return entityJavaFile.toString();
  }

}
