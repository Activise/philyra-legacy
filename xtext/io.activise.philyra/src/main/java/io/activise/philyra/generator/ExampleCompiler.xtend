package io.activise.philyra.generator

import com.google.inject.Guice
import com.google.inject.Inject
import io.activise.philyra.PhilyraRuntimeModule
import io.activise.philyra.philyra.ECompilationUnit
import java.io.ByteArrayInputStream
import java.io.InputStream
import org.eclipse.emf.common.util.URI
import org.eclipse.emf.mwe.utils.StandaloneSetup
import org.eclipse.xtext.resource.XtextResource
import org.eclipse.xtext.resource.XtextResourceSet
import io.activise.philyra.PhilyraStandaloneSetup
import java.nio.file.Path

class ExampleCompiler {
  @Inject XtextResourceSet resourceSet;

  def static ExampleCompiler create() {
    new StandaloneSetup().setPlatformUri("../")
    val compiler = new ExampleCompiler()
    val injector = new PhilyraStandaloneSetup().createInjectorAndDoEMFRegistration()
    injector.injectMembers(compiler)
    compiler.resourceSet.addLoadOption(XtextResource.OPTION_RESOLVE_ALL, Boolean.TRUE)
    return compiler
  }

  def ECompilationUnit runGenerator(String intput) {
    return runGenerator(new ByteArrayInputStream(intput.getBytes()))
  }

  def ECompilationUnit runGenerator(InputStream in) {
    val resource = resourceSet.createResource(URI.createURI("dummy:/inmemory.pya"))
    resource.load(in, resourceSet.getLoadOptions())
    var result = resource.getContents().get(0)
    if (result instanceof ECompilationUnit) {
      return result
    }
//    val issues = validator.validate(resource, CheckMode.ALL, CancelIndicator.NullImpl)
//    if (!issues.empty) {
//      issues.forEach[System.err.println(it)]
//      return
//    }
  }

  def ECompilationUnit runGenerator(Path path) {
    return runGenerator(URI.createFileURI(path.toAbsolutePath.toString))
  }

  def ECompilationUnit runGenerator(URI uri) {
    val resource = resourceSet.getResource(uri, true)
    val result = resource.getContents().get(0)
    if (result instanceof ECompilationUnit) {
      return result
    }
  }
}
