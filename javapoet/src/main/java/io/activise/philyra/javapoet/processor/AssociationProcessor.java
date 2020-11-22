package io.activise.philyra.javapoet.processor;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.AnnotationSpec.Builder;
import com.squareup.javapoet.FieldSpec;
import io.activise.philyra.javapoet.EntityIndex;
import io.activise.philyra.philyra.EAttribute;
import io.activise.philyra.philyra.EEntity;
import io.activise.philyra.philyra.ERelationType;

public class AssociationProcessor implements AttributeProcessor<FieldSpec.Builder> {
  @Override
  public void process(EntityIndex index, EEntity entity, EAttribute attribute, FieldSpec.Builder context) {
    
    if (attribute.getRelationType() == ERelationType.ONE_TO_MANY) {
      var generationContext = index.getGenerationContext(entity);
      generationContext.addRelation(attribute);
      context.addAnnotation(oneToManyAnnotation(attribute));
    } else {
      if (attribute.getType() instanceof EEntity) {
        var attributeType = (EEntity) attribute.getType();
        var attributeTypeGenerationContext = index.getGenerationContext(attributeType);
        var opposite = attributeTypeGenerationContext.getRelation(attribute);

        opposite.ifPresent(o -> {
          if (o.getRelationType() == ERelationType.ONE_TO_MANY) {
            context.addAnnotation(manyToOneAnnotation());
          }
        });
      }
    }
  }

  private AnnotationSpec manyToOneAnnotation() {
    var manyToOneAnnotation = AnnotationSpec.builder(ManyToOne.class);
    manyToOneAnnotation.addMember("fetchType", "$T.$L", FetchType.class, FetchType.LAZY);
    return manyToOneAnnotation.build();
  }

  private AnnotationSpec oneToManyAnnotation(EAttribute attribute) {
    var oneToManyAnnotation = AnnotationSpec.builder(OneToMany.class);
    oneToManyAnnotation.addMember("mappedBy", "$S", attribute.getOpposite().getName()).build();
    oneToManyAnnotation.addMember("cascade", "$T.$L", CascadeType.class, CascadeType.ALL);
    oneToManyAnnotation.addMember("orphanRemoval", "$L", true);
    oneToManyAnnotation.addMember("fetchType", "$T.$L", FetchType.class, FetchType.LAZY);
    return oneToManyAnnotation.build();
  }

}
