package io.activise.philyra.javapoet;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.lang.model.element.Modifier;
import javax.persistence.Table;
import org.eclipse.xtext.EcoreUtil2;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import com.squareup.javapoet.TypeSpec.Builder;
import io.activise.philyra.api.EntityDslTranspiler;
import io.activise.philyra.javapoet.processor.AssociationProcessor;
import io.activise.philyra.javapoet.processor.AttributeModifiersProcessor;
import io.activise.philyra.javapoet.processor.AttributeProcessor;
import io.activise.philyra.javapoet.utils.StringUtils;
import io.activise.philyra.philyra.EAttribute;
import io.activise.philyra.philyra.ECompilationUnit;
import io.activise.philyra.philyra.EEntity;
import io.activise.philyra.util.PhilyraLangUtil;

public class JavapoetTranspiler implements EntityDslTranspiler {
  private final EntityIndex entityIndex = new EntityIndex();
  private final IQualifiedNameProvider nameProvider = new DefaultDeclarativeQualifiedNameProvider();

  private final Set<AttributeProcessor<FieldSpec.Builder>> attributeProcessors =  new HashSet<>(Arrays.asList(
      new AssociationProcessor(), new AttributeModifiersProcessor()
  ));

  @Override
  public void transpile(ECompilationUnit compilationUnit) {
    var entities = EcoreUtil2.getAllContentsOfType(compilationUnit, EEntity.class);
    
    for (var entity : entities) {
      var entityClass = TypeSpec.classBuilder(entity.getName()).addModifiers(Modifier.PUBLIC);
      var generationContext = entityIndex.getGenerationContext(entity);

      
      prepareClassMeta(generationContext, entity, entityClass);

      for (var attribute : entity.getAttributes()) {
        // TODO: Remove try 'n catch
        try {
          var className = determineTypeName(attribute);
          var fieldSpec = FieldSpec.builder(className, attribute.getName(), Modifier.PRIVATE);
          attributeProcessors.forEach(p -> p.process(entityIndex, entity, attribute, fieldSpec));

          entityClass.addField(fieldSpec.build());
          entityClass.addMethod(createGetter(className, attribute.getName()));
        } catch (Exception e) {
//          e.printStackTrace();
        }
      }

      generationContext.applyAnnotations(entityClass);

      var source = createJavaSource(entity, entityClass);

      System.out.println(source);
    }
  }

// @formatter:off
  private void prepareClassMeta(GenerationContext generationContext, EEntity entity, TypeSpec.Builder entityClass) {
    entityClass.addAnnotation(javax.persistence.Entity.class);
    
    String tableName = entity.getTableName();
    if (tableName != null && !tableName.isBlank()) {
      var tableAnnotation = AnnotationSpec.builder(Table.class).addMember("name", "$S", tableName);
      generationContext.registerAnnotation("table", tableAnnotation);
    }
  }
// @formatter:on

  private TypeName determineTypeName(EAttribute attribute) {
    var typeName = entityIndex.getTypeName(attribute).orElse(null);
    if (typeName == null) {
      throw new RuntimeException("The type " + attribute.getType().getName() + " is not supported.");
    }

    if (attribute.isArray()) {
      return ParameterizedTypeName.get(ClassName.get(List.class), typeName);
    } else {
      return typeName;
    }
  }

// @formatter:off
  private MethodSpec createGetter(TypeName typeName, String name) {
    return MethodSpec.methodBuilder("get" + StringUtils.capitalize(name))
        .addModifiers(Modifier.PUBLIC)
        .returns(typeName)
        .addStatement("return $N", name)
        .build();
  }
// @formatter:on

  private String createJavaSource(EEntity entity, Builder entityClass) {
    var packageDeclaration = PhilyraLangUtil.getPackage(entity);
    var packageName = nameProvider.getFullyQualifiedName(packageDeclaration).toString();
    var entityJavaFile = JavaFile.builder(packageName, entityClass.build()).build();
    return entityJavaFile.toString();
  }
}
