package cs3500.animator.model;

public interface IModel extends ReadOnlyModel{

  /**
   * This function will create a shape (out of rectangle, oval right now). User will store this
   * shape into two map data structures that will be used to represent the entire animation.
   *
   * @param shape This is a string that represents the shape user wants to create. This can be one
   *              of "rectangle", "oval".
   * @param name  This is a custom name the user can assign to this shape.
   * @throws IllegalArgumentException under 3 situations: 1.shape is null or "". 2.name is null or
   *                                  "". 3.shape user wants to create does not exist.
   */
  void createShape(String shape, String name);

  /**
   * Removes a shape and its corresponding list of motions from the animation.
   *
   * @param name User can delete a shape by passing in the custom name that was assigned in the
   *             beginning.
   * @throws IllegalArgumentException if the name given does not match existing shapes in the
   *                                  animation.
   */
  void removeShape(String name);

  /**
   * This function will add one motion (transition of attributes of a shape from a time to another)
   * into a list of motions to a corresponding shape.
   *
   * @param name        This is the custom name of the shape that user wants to add the motion to.
   * @param startTime   Start time of the shape.
   * @param startX      Start position X of the shape.
   * @param startY      Start position Y of the shape.
   * @param startWidth  Start width of the shape.
   * @param startHeight Start height of the shape.
   * @param startColorR Start red color of the shape.
   * @param startColorB Start blue color of the shape.
   * @param startColorG Start green color of the shape.
   * @param endTime     End time of the shape.
   * @param endX        End position X of the shape.
   * @param endY        End position Y of the shape.
   * @param endWidth    End width of the shape.
   * @param endHeight   End height of the shape.
   * @param endColorR   End red color of the shape.
   * @param endColorB   End blue color of the shape.
   * @param endColorG   End green color of the shape.
   * @throws IllegalArgumentException if the name given by the user does not exist in current
   *                                  animation.
   * @throws IllegalArgumentException if the given (starting and ending) position X or position Y is
   *                                  negative.
   * @throws IllegalArgumentException if the given colors (starting and ending) R, G, B are not
   *                                  within the range between 0 to 255 (including 0 to 255).
   * @throws IllegalArgumentException if the given width and height (starting and ending) are
   *                                  negative.
   */
  void addMotion(String name, int startTime, int startX, int startY, double startWidth,
      double startHeight, int startColorR, int startColorG, int startColorB,
      int endTime, int endX, double endY, double endWidth,
      double endHeight, int endColorR,
      int endColorG, int endColorB);

  /**
   * Return the complete animation as a string that discribes a shape and its list of motions.
   * declares a rectangle shape named R
   * shape R rectangle
   * describes the motions of shape R, between two moments of animation:
   * t == tick
   * (x,y) == position
   * (w,h) == dimensions
   * (r,g,b) == color (with values between 0 and 255)
   *                  start                           end
   *        --------------------------    ----------------------------
   *        t  x   y   w  h   r   g  b    t   x   y   w  h   r   g  b
   * shape R rectangle[n]
   * motion R 1  200 200 50 100 255 0  0    10  200 200 50 100 255 0  0[n]
   * motion R 10 200 200 50 100 255 0  0    50  300 300 50 100 255 0  0[n]
   * motion R 50 300 300 50 100 255 0  0    51  300 300 50 100 255 0  0[n]
   * motion R 51 300 300 50 100 255 0  0    70  300 300 25 100 255 0  0[n]
   * motion R 70 300 300 25 100 255 0  0    100 200 200 25 100 255 0  0[n]
   * where [n] is the new line character.
   * @return the formated string as above.
   * @throws IllegalStateException if any shape in the Animation has overlapping attributes or
   *                               teleportation.
   */
  String toString();

  /**
   * Remove a motion from a list of motion for a given shape.
   *
   * @param name  arbitary str name that user assigned to their shape upon initialization.
   * @param index the index of the motion the user wants to delete. This index needs to be either
   *              the first or the last motion in the list of motions.
   * @throws IllegalArgumentException if the str name given by the user does not exist or null in
   *                                  current animation.
   * @throws IllegalArgumentException if the index given by the user is not the first or the last
   *                                  motion in a list of motions.
   */
  void removeMotion(String name, int index);

  /**
   * Allow the controller to set the current tick of the system.
   * @param newTick give the new tick that we want to assign to the model.
   */
  void setTick(int newTick);

  /**
   * Check the model by first sorting list of motion of each shape and check if each list has
   *  teleportation.
   */
  void sortAndCheckListsOfMotions();
}
