package io.activise.philyra.scoping

import com.google.inject.Inject
import io.activise.philyra.philyra.ECompilationUnit
import io.activise.philyra.philyra.EImport
import java.util.HashMap
import org.eclipse.emf.ecore.EObject
import org.eclipse.xtext.naming.QualifiedName
import org.eclipse.xtext.resource.EObjectDescription
import org.eclipse.xtext.resource.IEObjectDescription
import org.eclipse.xtext.resource.impl.DefaultResourceDescriptionStrategy
import org.eclipse.xtext.scoping.impl.ImportUriResolver
import org.eclipse.xtext.util.IAcceptor

import static extension org.eclipse.xtext.EcoreUtil2.*

class PhilyraResourceDescriptionStrategy extends DefaultResourceDescriptionStrategy {
  public static final String IMPORTS = "imports"
  @Inject
  ImportUriResolver uriResolver

  override createEObjectDescriptions(EObject eObject, IAcceptor<IEObjectDescription> acceptor) {
    if(eObject instanceof ECompilationUnit) {
      this.createEObjectDescriptionForCompilationUnit(eObject, acceptor)
      return true
    }
    else {
      super.createEObjectDescriptions(eObject, acceptor)
    }
  }

  def void createEObjectDescriptionForCompilationUnit(ECompilationUnit compilationUnit, IAcceptor<IEObjectDescription> acceptor) {
    val uris = newArrayList()
    compilationUnit.getAllContentsOfType(EImport).forEach[uris.add(uriResolver.apply(it))]
//		compilationUnit.elements.filter(EImport).forEach[uris.add(uriResolver.apply(it))]
    val userData = new HashMap<String, String>()
    userData.put(IMPORTS, uris.join(","))
    acceptor.accept(EObjectDescription.create(QualifiedName.create(compilationUnit.eResource.URI.toString), compilationUnit, userData))
  }
}