package cs3500.animator.model;

import java.awt.Color;

/**
 * This interface represents a geometric shape. It contains a lists of operations such as get the
 * name of the shape, compare different shapes.
 */
public interface IShape {

  /**
   * This function will find and return the custom name of each shape user created.
   *
   * @return custom str of a shape.
   */
  String getShapeName();

  /**
   * This function will find and return the custom name of each shape user created.
   *
   * @return custom str of a shape that user assigned at shape creation.
   */
  String getName();

  /**
   * This will aid the equals function that we will rewrite below.
   *
   * @return aa
   */
  int hashCode();

  /**
   * This will compare two different shapes based on its attributes.
   *
   * @param that is another shape that we would like to compare our current shape to.
   * @return boolean value of whther the two shapes are equal.
   */
  boolean equals(Object that);

  Color getColor();
  Position2D getPosition();
  double getWidth();
  double getHeight();
  DifferentShapes getShape();


}
