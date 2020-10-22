package io.activise.entitydsl.api.transpiler;

public class TranspilationResult {
  private String id;
  private String group;
  private String source;

  public TranspilationResult(String id, String group, String source) {
    this.id = id;
    this.group = group;
    this.source = source;
  }

  public String getId() {
    return id;
  }

  public String getGroup() {
    return group;
  }

  public String getSource() {
    return source;
  }

}
