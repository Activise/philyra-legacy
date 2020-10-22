package io.activise.entitydsl.maven;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import io.activise.entitydsl.javapoet.JavapoetTranspiler;

@Mojo(name = "generate", defaultPhase = LifecyclePhase.GENERATE_SOURCES)
public class PhilyraMojo extends AbstractMojo {
  @Parameter(required = true)
  private File dslLocation;

  @Parameter(required = true)
  private File output;

  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    // try {
    //   var compiler = new AntlrCompiler();
    //   var source = Files.readString(Path.of(dslLocation.toURI()), StandardCharsets.UTF_8);
    //   var compilationUnit = compiler.parse(source);
    //   var transpiler = new JavapoetTranspiler();
    //   var transpilationResults = transpiler.transpile(compilationUnit);

    //   for (var transpilationResult : transpilationResults) {
    //     String relativePath = transpilationResult.getGroup().replace('.', '/');
    //     var sourcePath = Path.of(output.getAbsolutePath(), relativePath, transpilationResult.getId() + ".java");
    //     Files.createDirectories(sourcePath.getParent());
    //     Files.writeString(sourcePath, transpilationResult.getSource(), StandardCharsets.UTF_8);
    //   }
    // } catch (IOException e) {
    //   e.printStackTrace();
    // }
  }

}
