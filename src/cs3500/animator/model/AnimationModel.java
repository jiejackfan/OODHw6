package cs3500.animator.model;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementation of the animation operation interface.
 */
public class AnimationModel implements AnimationOperation {

  /**
   * This final hash map structure will store the user given name as the key and the key's
   * corresponding shapes as value.
   */
  private final Map<String, IShape> nameMap;

  /**
   * This final hash map structure will store the entire animation. key: shape. value: the shape's
   * list of motions.
   */
  private final Map<IShape, List<Motion>> animation;

  /**
   * Constructor for model, initialize two empty hash maps.
   */
  public AnimationModel() {
    this.nameMap = new LinkedHashMap<>();
    this.animation = new LinkedHashMap<>();
  }

  @Override
  public void createShape(String shape, String name) {
    if (name == null || name.equals("")) {
      throw new IllegalArgumentException("The name cannot be null or empty.");
    }
    if (shape == null || shape.equals("")) {
      throw new IllegalArgumentException("Invalid shape input.");
    }
    switch (shape.toLowerCase()) {
      case "rectangle":
        nameMap.put(name, new Rectangle(name));
        animation.put(nameMap.get(name), new ArrayList<>());
        break;
      case "oval":
        nameMap.put(name, new Oval(name));
        animation.put(nameMap.get(name), new ArrayList<>());
        break;
      // More shapes can be implemented here.
      default:
        throw new IllegalArgumentException("Please provide a valid shape.");
    }
  }

  @Override
  public void removeShape(String name) {
    // Check whether the shape exist in the animation
    if (!nameMap.containsKey(name)) {
      throw new IllegalArgumentException("The given shape is not in the animation.");
    }
    animation.remove(nameMap.get(name));
    nameMap.remove(name);
  }

  @Override
  public void addMotion(String name, int startTime, int startX, int startY, double startWidth,
                        double startHeight, int startColorR, int startColorG, int startColorB,
                        int endTime, int endX, double endY, double endWidth,
                        double endHeight, int endColorR, int endColorG,
                        int endColorB) {
    // Check whether the given parameters of color are valid, if they are valid, pass into the color
    // constructor, if not, throw an illegal argument exception.
    if (!nameMap.containsKey(name)) {
      throw new IllegalArgumentException("The name does not exist in current shapes.");
    }
    if (startColorR < 0 || startColorR > 255 || endColorR < 0 || endColorR > 255
            || startColorG < 0 || startColorG > 255 || endColorG < 0 || endColorG > 255
            || startColorB < 0 || startColorB > 255 || endColorB < 0 || endColorB > 255) {
      throw new IllegalArgumentException("The RGB value must be within the range.");
    }

    // Other parameters are check when constructing the motion, and the parameters of position are
    // checked in the position2D in the constructor of class position2D.
    animation.get(nameMap.get(name)).add(
            new Motion(startTime, new Position2D(startX, startY), startWidth, startHeight,
                    new Color(startColorR, startColorG, startColorB), endTime,
                    new Position2D(endX, endY), endWidth, endHeight,
                    new Color(endColorR, endColorG, endColorB)));
  }


  @Override
  public String toString() {
    String output = "";
    // If there is no shapes on the animation, print an empty string
    if (nameMap.entrySet().isEmpty()) {
      return output;
    }
    for (Map.Entry mapPair : nameMap.entrySet()) {
      String name = (String) mapPair.getKey();
      output = output + "Shape " + name + " " + nameMap.get(name).getShapeName() + "\n";
      output = output + listOfMotionsToString(name, animation.get(nameMap.get(name)));
    }
    return output;
  }

  /**
   * Helper function for toString(). Convert the given list of motions to lines of string based on
   * toString()'s rules.
   *
   * @param name         of the shape that we want to print out the list of motions.
   * @param listOfMotion the list of motions associated to the name provided by the user.
   * @return several lines of string thats a list of motions.
   * @throws IllegalArgumentException if there is a teleporation.
   */
  private String listOfMotionsToString(String name, List<Motion> listOfMotion) {

    listOfMotion.sort(new SortByStartTime());

    if (!checkValidAnimation(listOfMotion)) {
      throw new IllegalStateException("There is teleportation or overlap in this shape, this "
              + "shape will be deleted.");
    }

    String result = "";
    for (Motion m : listOfMotion) {
      result = result + "motion " + name + " " + m.toString() + "\n";
    }
    return result;
  }

  /**
   * Checks whether a list of motion we from a particular shape from the input file (the map data
   * structure) is valid.
   *
   * @return true if valid, false if not.
   */
  private boolean checkValidAnimation(List<Motion> listOfMotion) {
    boolean result = true;
    // If there is no motion, the animation is valid
    if (animation.entrySet().isEmpty()) {
      return true;
    }
    // Check whether the list of motions for each shape is valid
    // and combine all results
    for (int i = 0; i < listOfMotion.size() - 1; i++) {
      result = listOfMotion.get(i).getEndTime() == listOfMotion.get(i + 1).getStartTime();
      result = result
              && (listOfMotion.get(i).getEndColor().equals(
              listOfMotion.get(i + 1).getStartColor()));
      result = result
              && (listOfMotion.get(i).getEndHeight() == listOfMotion.get(i + 1).getStartHeight());
      result = result
              && (listOfMotion.get(i).getEndWidth() == listOfMotion.get(i + 1).getStartWidth());
      result = result
              && (listOfMotion.get(i).getEndPosition().getX()
              == listOfMotion.get(i + 1).getStartPosition().getX());
      result = result
              && (listOfMotion.get(i).getEndPosition().getY()
              == listOfMotion.get(i + 1).getStartPosition().getY());
      // If there is a mismatch, delete the shape and throw new illegal argument.
      if (!result) {
        break;
      }
    }
    return result;
  }

  @Override
  public List<IShape> getAnimation(int time) {

    List<IShape> shapesAtTime = new ArrayList<>();

    if (time < 1) {
      throw new IllegalArgumentException("Invalid time.");
    }

    //go through all shapes in map, add to shapeAtTime if we find a shape that have motion at the
    //  exact time
    for (Map.Entry mapPair : animation.entrySet()) {
      IShape tmpShape = (IShape) mapPair.getKey();
      Motion tmpMotion;
      if (isTimeInListOfMotion((List<Motion>) mapPair.getValue(), time)) {
        tmpMotion = findMotion((List<Motion>) mapPair.getValue(), time);
        shapesAtTime.add(buildShape(tmpShape.getShapeName(), tmpMotion, time, tmpShape.getName()));
      }
    }
    return shapesAtTime;
  }

  /**
   * Helper for getAnimation(). Checks the list of motion to see if the given time exist.
   *
   * @param listOfMotion of a shape provided by the user.
   * @param time         that user wants to validate that exist.
   * @return true if the time exist.
   */
  private boolean isTimeInListOfMotion(List<Motion> listOfMotion, int time) {
    int startTime = listOfMotion.get(0).getStartTime();
    int endTime = listOfMotion.get(listOfMotion.size() - 1).getEndTime();

    return (time >= startTime && time <= endTime);
  }


  /**
   * Helper for getAnimation(). Builds a copy of a particular shape that contains the color,
   * position, width, height at a given time.
   *
   * @param shape     the kind of shape that user wants to build.
   * @param tmpMotion the motion that user wants to calculate the color, position, width, height of
   *                  the shape.
   * @param time      time at which user wants to calculate new shape characteristics at.
   * @return the newly created shape.
   * @throws IllegalArgumentException if the shape does not exist.
   */
  private IShape buildShape(String shape, Motion tmpMotion, int time, String name) {

    double ratio = (double) (time - tmpMotion.getStartTime())
            / (tmpMotion.getEndTime() - tmpMotion.getStartTime());
    Color color = new Color(
            (int) (ratio * (tmpMotion.getEndColor().getRed() - tmpMotion.getStartColor().getRed())
                    + tmpMotion.getStartColor().getRed()),
            (int) (ratio * (tmpMotion.getEndColor().getGreen()
                    - tmpMotion.getStartColor().getGreen())
                    + tmpMotion.getStartColor().getGreen()),
            (int) (ratio * (tmpMotion.getEndColor().getBlue()
                    - tmpMotion.getStartColor().getBlue())
                    + tmpMotion.getStartColor().getBlue()));
    Position2D position = new Position2D(
            ratio * (tmpMotion.getEndPosition().getX() - tmpMotion.getStartPosition().getX())
                    + tmpMotion.getStartPosition().getX(),
            ratio * (tmpMotion.getEndPosition().getY() - tmpMotion.getStartPosition().getY())
                    + tmpMotion.getStartPosition().getY());
    double width = ratio * (tmpMotion.getEndWidth() - tmpMotion.getStartWidth())
            + tmpMotion.getStartWidth();
    double height = ratio * (tmpMotion.getEndHeight() - tmpMotion.getStartHeight())
            + tmpMotion.getStartHeight();

    switch (shape) {
      case "rectangle":
        return new Rectangle(color, position, width, height, name);
      case "oval":
        return new Oval(color, position, width, height, name);
      // More shapes can be implemented here.
      default:
        throw new IllegalArgumentException("Please provide a valid shape name.");
    }
  }

  /**
   * Find and return the motion that has the given time in a list of motions.
   *
   * @param listOfMotion we want to find the motion in.
   * @param time         the time of the motion we want to find.
   * @return the motion that contains the time.
   */
  private Motion findMotion(List<Motion> listOfMotion, int time) {
    for (Motion tmpMotion : listOfMotion) {
      int startTime = tmpMotion.getStartTime();
      int endTime = tmpMotion.getEndTime();

      if (time >= startTime && time <= endTime) {
        return new Motion(tmpMotion);
      }
    }
    throw new IllegalArgumentException("Should not reach this point.");
  }

  @Override
  public void removeMotion(String name, int index) {
    // Check if the name of the shape we want to remove actually exists
    if (!nameMap.containsKey(name)) {
      throw new IllegalArgumentException("The shape you want to remove does not exist.");
    }
    IShape tmpShape = nameMap.get(name);

    // Check if the index of the shape you want to remove is either the first or the last. If the
    //  index is not first or last, throw illegal argument.
    if (index != 0 && index != (animation.get(tmpShape).size() - 1)) {
      throw new IllegalArgumentException("The motion is not the first or the last in the list, "
              + "can't be remove as of right now.");
    }

    List<Motion> tmpListOfMotion = animation.get(tmpShape);
    tmpListOfMotion.remove(index);
  }


  /**
   * This is a comparator class that we will use when trying to sort a list of motion based on its
   * start time.
   */
  static class SortByStartTime implements Comparator<Motion> {
    // Used for sorting in ascending order of
    // start time
    public int compare(Motion a, Motion b) {
      return a.getStartTime() - b.getEndTime();
    }
  }

}
