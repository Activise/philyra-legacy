package io.activise.philyra.util

import io.activise.philyra.philyra.EAbstractType
import io.activise.philyra.philyra.EType
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider
import org.eclipse.xtext.naming.IQualifiedNameProvider

import static extension io.activise.philyra.util.PhilyraLangUtil.getPackage

class AbstractTypeInfo {
  static extension IQualifiedNameProvider = new DefaultDeclarativeQualifiedNameProvider()
  
  String fullQualifiedName
  String packageName
  String simpleName
  
  new(String fullQualifiedName, String packageName, String simpleName) {
    this.fullQualifiedName = fullQualifiedName
    this.packageName = packageName
    this.simpleName = simpleName
  }
  
  def String getFullQualifiedName() {
    return fullQualifiedName
  }
  
  def String getPackageName() {
    return packageName
  }
  
  def String getSimpleName() {
    return simpleName
  }
  
  def static AbstractTypeInfo from(EAbstractType abstractType) {
    if (abstractType instanceof EType) {
      return from(abstractType.target)
    }
    
    return new AbstractTypeInfo(abstractType.fullyQualifiedName.toString(), abstractType.package.fullyQualifiedName.toString(), abstractType.name)
  }
  
  def static AbstractTypeInfo from(String fullQualifiedName) {
    val lastPointIndex = fullQualifiedName.lastIndexOf('.')
    val packageFqn = fullQualifiedName.substring(0, lastPointIndex)
    val simpleName = fullQualifiedName.substring(lastPointIndex + 1)
    return new AbstractTypeInfo(fullQualifiedName, packageFqn, simpleName);
  }
  
}