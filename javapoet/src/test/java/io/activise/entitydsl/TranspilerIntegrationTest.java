package io.activise.entitydsl;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import io.activise.entitydsl.api.transpiler.TranspilationResult;
import io.activise.entitydsl.javapoet.JavapoetTranspiler;

public class TranspilerIntegrationTest {
  // @Test
  // public void example() throws Exception {
  //   String source = getSource("example.txt");
  //   var compiler =  new AntlrCompiler();
  //   var compilationUnit = compiler.parse(source);
  //   var transpiler = new JavapoetTranspiler();
  //   transpiler.transpile(compilationUnit).stream().map(TranspilationResult::getSource).forEach(System.out::println);
  // }

  // private String getSource(String path) throws Exception {
  //   return Files.readString(Path.of(getClass().getClassLoader().getResource(path).toURI()), StandardCharsets.UTF_8);
  // }

}
