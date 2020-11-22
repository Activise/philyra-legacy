package io.activise.philyra.scoping

import com.google.common.base.Splitter
import com.google.inject.Inject
import com.google.inject.Provider
import java.util.LinkedHashSet
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.ecore.resource.Resource
import org.eclipse.xtext.EcoreUtil2
import org.eclipse.xtext.resource.IResourceDescription
import org.eclipse.xtext.scoping.impl.ImportUriGlobalScopeProvider
import org.eclipse.xtext.util.IResourceScopeCache
import io.activise.philyra.philyra.PhilyraPackage

class PhilyraGlobalScopeProvider extends ImportUriGlobalScopeProvider {
  static final Splitter SPLITTER = Splitter.on(',');

  @Inject
  IResourceDescription.Manager descriptionManager;

  @Inject
  IResourceScopeCache cache;

  override protected getImportedUris(Resource resource) {
    return cache.get(PhilyraGlobalScopeProvider.getSimpleName(), resource, new Provider<LinkedHashSet<URI>>() {
      override get() {
        val importUris = collectImportUris(resource, new LinkedHashSet<URI>(5))
        importUris.removeIf[!EcoreUtil2.isValidUri(resource, it)]
        return importUris
      }

      def LinkedHashSet<URI> collectImportUris(Resource resource, LinkedHashSet<URI> importUris) {
        val resourceDescription = descriptionManager.getResourceDescription(resource)
        val compilationUnits = resourceDescription.getExportedObjectsByType(PhilyraPackage.Literals.ECOMPILATION_UNIT)
        
        compilationUnits
          .map[getUserData(PhilyraResourceDescriptionStrategy.INCLUDES)]
          .filter[!isNullOrEmpty].forEach[
            SPLITTER.split(it)
              .map[URI.createURI(it).resolve(resource.URI)]
              .filter[importUris.add(it)]
              .map[resource.getResourceSet().getResource(it, true)]
              .forEach[collectImportUris(it, importUris)]
          ]
   
        return importUris
      }
    });
  }
}
