package io.activise.entitydsl.api.lang;

public interface AssociationDetails {
  Attribute getTargetAttribute();
  
  boolean isParent();

}