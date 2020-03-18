package cs3500.animator.model;

import java.awt.Color;

/**
 * An oval shape implementation that is extending the abstract shape class. This class will be used
 * mainly during initialization. The purpose is to assign "oval" to shapeName variable in the
 * abstract class so later on we know this shape is an oval.
 */
public class Oval extends AShape {

  /**
   * Constructor of this oval shape. First call constructor of abstract class then assign to the
   * abtract class's shapeName variable.
   */
  public Oval(String name) {
    super(name);
    this.shape = "oval";
  }

  /**
   * Constuctor 2 of this oval shape. Create a abstract shape that holds all characteristic of the
   * oval shape and change the shapeName variable.
   *
   * @param color    is color of oval at initialization.
   * @param position is position of oval at initialization.
   * @param width    is width of oval at initialization.
   * @param height   is height of oval at initialization.
   */
  public Oval(Color color, Position2D position, double width, double height, String name) {
    super(color, position, width, height, name);
    this.shape = "oval";
  }

}
