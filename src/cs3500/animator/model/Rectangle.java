package cs3500.animator.model;

import java.awt.Color;

/**
 * A rectangle shape implementation that is extending the abstract shape class. This class will be
 * used mainly during initialization. The purpose is to assign "rectange" to shapeName variable in
 * the abstract class so later on we know this shape is a rectangle.
 */
public class Rectangle extends AShape {

  /**
   * Constructor to a rectangle that assigns "rectangle" to the shapeName variable in the abstract
   * class.
   */
  public Rectangle(String name) {
    super(name);
    this.shape = "rectangle";
  }

  /**
   * Constructor to a rectangle that assigns the "rectangle: to the shapeName variable and also
   * assign some characteristics to this shape and store all of these variable to the abstract
   * class.
   *
   * @param color    is color of rectangle at initialization.
   * @param position is position of rectangle at initialization.
   * @param width    is width of rectangle at initialization.
   * @param height   is height of rectangle at initialization.
   */
  public Rectangle(Color color, Position2D position, double width, double height, String name) {
    super(color, position, width, height, name);
    this.shape = "rectangle";
  }

}
