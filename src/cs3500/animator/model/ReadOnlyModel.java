package cs3500.animator.model;

import java.util.List;

/**
 * This is the interface of the main logic of this animation model. Animation model interface will
 * can create a shape, remove a shape, add motions to a shape, check if a listOfMotion is valid, and
 * convert current animation to strings.
 */
public interface ReadOnlyModel {


  /**
   * This will be a function that builds a list of shapes that can be passed to view. Each shape
   * stores its Position2D, Color, width, height at a particular time. View will use this list of
   * shapes to draw each shape at a particular time.
   *
   * @param time at which each shape should be built with.
   * @return a list of shapes with updated information on whats happening in that shape.
   * @throws IllegalArgumentException if the given time is less than 1.
   */
  List<IShape> getAnimation(int time);

  /**
   * Get the current tick of the model.
   * @return an integer that represents current tick.
   */
  int getCurrentTick();

  /**
   * Get the max tick that the model will run to.
   * @return integer that represents max tick of the animation.
   */
  int getMaxTick();

}
