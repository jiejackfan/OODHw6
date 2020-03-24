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
   * Return the complete animation as a string that discribes a shape and its list of motions.
   * declares a rectangle shape named R shape R rectangle describes the motions of shape R, between
   * two moments of animation: t == tick (x,y) == position (w,h) == dimensions (r,g,b) == color
   * (with values between 0 and 255) start                           end --------------------------
   *   ---------------------------- t  x   y   w  h   r   g  b    t   x   y   w  h   r   g  b shape
   * R rectangle[n] motion R 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0[n] motion R
   * 10 200 200 50 100 255 0  0    50  300 300 50 100 255 0  0[n] motion R 50 300 300 50 100 255 0
   * 0    51  300 300 50 100 255 0  0[n] motion R 51 300 300 50 100 255 0  0    70  300 300 25 100
   * 255 0  0[n] motion R 70 300 300 25 100 255 0  0    100 200 200 25 100 255 0  0[n] where [n] is
   * the new line character.
   *
   * @return the formated string as above.
   * @throws IllegalStateException if any shape in the Animation has overlapping attributes or
   *                               teleportation.
   */
  String toString();

  /**
   * Get the current tick of the model.
   *
   * @return an integer that represents current tick.
   */
  int getCurrentTick();

  /**
   * Get the max tick that the model will run to.
   *
   * @return integer that represents max tick of the animation.
   */
  int getMaxTick();

  void setCanvas(int x, int y, int w, int h);

  int getCanvasX();

  int getCanvasY();

  int getCanvasWidth();

  int getCanvasHeight();

  List<IShape> getAllShapes();

  List<Motion> getAllMotionsOfShape(IShape shape);


}
