package io.activise.philyra;

import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import io.activise.philyra.generator.ExampleCompiler;
import io.activise.philyra.javapoet.JavapoetTranspiler;

public class TranspilerIntegrationTest {
  @Test
  public void example() throws Exception {
    Path source = getSource("example.pya");
    var exampleCompiler = ExampleCompiler.create();
    var result = exampleCompiler.runGenerator(source);
    var transpiler = new JavapoetTranspiler();
    transpiler.transpile(result);
  }

  private Path getSource(String path) throws Exception {
    return Path.of(getClass().getClassLoader().getResource(path).toURI());
  }

}
