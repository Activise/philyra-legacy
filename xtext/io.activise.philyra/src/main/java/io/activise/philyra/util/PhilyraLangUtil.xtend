package io.activise.philyra.util

import io.activise.philyra.philyra.EAbstractType
import io.activise.philyra.philyra.EPackageDeclaration
import org.eclipse.xtext.EcoreUtil2

class PhilyraLangUtil {
  def static EPackageDeclaration getPackage(EAbstractType abstractType) {
    return EcoreUtil2.getContainerOfType(abstractType, EPackageDeclaration)
  }
  
}