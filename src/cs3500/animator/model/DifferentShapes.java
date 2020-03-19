package cs3500.animator.model;

import java.util.Objects;

public enum DifferentShapes {
  rectangle("rectangle"), oval("oval");

  private final String shape;

  DifferentShapes(String shape) {
    this.shape = shape;
  }

  public String getShape() {
    return shape;
  }

}
