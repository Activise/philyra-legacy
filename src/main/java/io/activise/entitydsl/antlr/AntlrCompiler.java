package io.activise.entitydsl.antlr;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import io.activise.entitydsl.EntityDslCompiler;
import io.activise.entitydsl.antlr.EntityDslParser.CompilationUnitContext;
import io.activise.entitydsl.api.CompilationUnit;

public class AntlrCompiler implements EntityDslCompiler {
  private static class CompilationUnitListener extends EntityDslBaseListener {
    private CompilationUnit compilationUnit;

    @Override
    public void enterCompilationUnit(CompilationUnitContext ctx) {
      compilationUnit = new AntlrCompilationUnit(ctx);
    }

    public CompilationUnit getCompilationUnit() {
      return compilationUnit;
    }
  }

  @Override
  public CompilationUnit compile(String source) {
    EntityDslParser parser = createParser(source);
    ParseTreeWalker walker = new ParseTreeWalker();

    var compilationUnitListener = new CompilationUnitListener();
    walker.walk(compilationUnitListener, parser.compilationUnit());

    return compilationUnitListener.getCompilationUnit();
  }

  private EntityDslParser createParser(String source) {
    EntityDslLexer lexer = new EntityDslLexer(CharStreams.fromString(source));
    CommonTokenStream tokens = new CommonTokenStream(lexer);
    return new EntityDslParser(tokens);
  }

}