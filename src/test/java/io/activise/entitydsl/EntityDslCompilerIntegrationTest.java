package io.activise.entitydsl;

import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import io.activise.entitydsl.antlr.AntlrCompiler;
import io.activise.entitydsl.api.CompilationUnit;

public class EntityDslCompilerIntegrationTest {
  @Test
  public void example() throws Exception {
    String source = getSource("example.txt");

    EntityDslCompiler compiler = new AntlrCompiler();
    CompilationUnit compilationUnit = compiler.compile(source);

    long savedTime = System.currentTimeMillis();
    compilationUnit.getEntities().forEach(e -> {
      e.getAttributes().forEach(a -> {
        a.getAttributeOptions().forEach(o -> {
          System.out.println(o.getName());
        });
      });
    });
    long newTime = System.currentTimeMillis();
    System.out.println(newTime - savedTime + "ms");
  }

  private String getSource(String path) throws Exception {
    Path sourcePath = Path.of(getClass().getClassLoader().getResource(path).toURI());
    return new String(Files.readAllBytes(sourcePath));
  }

}
