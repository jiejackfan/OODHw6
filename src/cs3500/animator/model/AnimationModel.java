package cs3500.animator.model;

import cs3500.animator.util.AnimationBuilder;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * The animation model implementation. It will implement all methods declared in IModel and
 * ReadOnlyModel. This model will be the main storage of the animation. It will store shapes and
 * each shape's {@code List<Motions>} in a LinkedHashMap.
 */
public class AnimationModel implements IModel {

  /**
   * This final hash map structure will store the user given name as the key and the key's
   * corresponding shapes as value.
   */
  private final Map<String, IShape> nameMap;

  /**
   * This final hash map structure will store the entire animation.
   *  key: a shape.
   *  value: that key shape's list of motions.
   */
  private final Map<IShape, List<Motion>> animation;


  private int maxTick;

  private int canvasX;
  private int canvasY;
  private int canvasWidth;
  private int canvasHeight;

  private int currentTick = 0;

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

    nameMap.put(name, new Shape(name, DifferentShapes.valueOf(shape.toLowerCase())));
    animation.put(nameMap.get(name), new ArrayList<>());
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
    for (Map.Entry<String, IShape> mapPair : nameMap.entrySet()) {
      String name = (String) mapPair.getKey();
      output = output + "shape " + name + " " + nameMap.get(name).getShapeName() + "\n";
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

    //listOfMotion.sort(new SortByStartTime());

    /*
    if (!checkValidAnimation(listOfMotion)) {
      throw new IllegalStateException("There is teleportation or overlap in this shape, this "
              + "shape will be deleted.");
    }
    */

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

    //go through all shapes in map, add to shapeAtTime if we find a shape that have motion at the
    //  exact time
    for (Map.Entry<IShape, List<Motion>> mapPair : animation.entrySet()) {
      IShape tmpShape = mapPair.getKey();
      Motion tmpMotion;
      if (isTimeInListOfMotion(mapPair.getValue(), time)) {
        tmpMotion = findMotion(mapPair.getValue(), time);
        shapesAtTime.add(buildShape(tmpShape.getShapeName(), tmpMotion, time, tmpShape.getName()));
      }
    }
    return shapesAtTime;
  }


  /**
   * Helper for getAnimation(). Checks the list of motion to see if the given time exist within
   *  the time range of that list of motion.
   *
   * @param listOfMotion of a shape provided by the user.
   * @param time         that user wants to validate that exist.
   * @return true if the time exist.
   */
  private boolean isTimeInListOfMotion(List<Motion> listOfMotion, int time) {
    int startTime = listOfMotion.get(0).getStartTime();
    //int endTime = listOfMotion.get(listOfMotion.size() - 1).getEndTime();

    //return (time >= startTime && time <= endTime);
    return (time >= startTime && time <= maxTick);
  }

  /**
   * Find and return a copyt of the motion that has the given time in a list of motions.
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
    return new Motion(listOfMotion.get(listOfMotion.size() - 1));
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
    //if the time has passed the ending time
    if (time > tmpMotion.getEndTime() && time <= getMaxTick()) {
      return new Shape(tmpMotion.getEndColor(), tmpMotion.getEndPosition(), tmpMotion.getEndWidth(),
              tmpMotion.getEndHeight(), name, DifferentShapes.valueOf(shape.toLowerCase()));
    }
    //if starttime the time given
    else if (time == tmpMotion.getStartTime()) {
      return new Shape(tmpMotion.getStartColor(), tmpMotion.getStartPosition(),
              tmpMotion.getStartWidth(), tmpMotion.getStartHeight(), name,
              DifferentShapes.valueOf(shape.toLowerCase()));
    }
    //else if the time has not passed ending time
    else {
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

      return new Shape(color, position, width, height, name,
              DifferentShapes.valueOf(shape.toLowerCase()));
    }
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

  @Override
  public int getCurrentTick() {
    return this.currentTick;
  }

  @Override
  public int getMaxTick() {
    int tmpMax = 0;
    int endTime;
    for (Map.Entry<IShape, List<Motion>> entry : animation.entrySet()) {
      //direct mutation of the list of motion to sort them in order.
      List<Motion> listOfMotion = entry.getValue();
      //listOfMotion.sort(new SortByStartTime());
      endTime = listOfMotion.get(listOfMotion.size() - 1).getEndTime();
      if (endTime > tmpMax) {
        tmpMax = endTime;
      }
    }
    return tmpMax;
  }

  @Override
  public void setTick(int newTick) {
    currentTick = newTick;
  }


  @Override
  public void sortAndCheckListsOfMotions() {
    if (nameMap.entrySet().isEmpty()) {
      return;
    }

    for (Map.Entry<IShape, List<Motion>> entry : animation.entrySet()) {
      entry.getValue().sort(new SortByStartTime());

      if (!checkValidAnimation(entry.getValue())) {
        throw new IllegalStateException("There is teleportation or overlap in this shape, this "
                + "shape will be deleted.");
      }
    }

    this.maxTick = getMaxTick();
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

  @Override
  public void setCanvas(int x, int y, int w, int h) {
    if (/*x < 0 || y < 0 || */w < 0 || h < 0) {
      throw new IllegalArgumentException("The canvas position and size must be positive.");
    }
    this.canvasX = x;
    this.canvasY = y;
    this.canvasWidth = w;
    this.canvasHeight = h;
  }

  @Override
  public List<IShape> getShapesBeginning() {
    List<IShape> shapesBeginnings = new ArrayList<>();
    for (Map.Entry<IShape, List<Motion>> mapPair : animation.entrySet()) {
      IShape tmpShape = mapPair.getKey();
      if (!mapPair.getValue().isEmpty()) {
        Motion tmpMotion = mapPair.getValue().get(0);
        shapesBeginnings.add(buildShape(tmpShape.getShapeName(), tmpMotion,
                tmpMotion.getStartTime(), tmpShape.getName()));
      }
    }
    return shapesBeginnings;
  }

  @Override
  public int getCanvasX() {
    return this.canvasX;
  }

  @Override
  public int getCanvasY() {
    return this.canvasY;
  }

  @Override
  public int getCanvasWidth() {
    return this.canvasWidth;
  }

  @Override
  public int getCanvasHeight() {
    return this.canvasHeight;
  }

  @Override
  public List<IShape> getAllShapes() {
    List<IShape> allShapes = new ArrayList<>();
    for (Map.Entry<String, IShape> mapPair : nameMap.entrySet()) {
      // make a copy of the shape
      allShapes.add(new Shape((Shape) mapPair.getValue()));
    }
    return allShapes;
  }

  @Override
  public List<Motion> getAllMotionsOfShape(IShape shape) {
    List<Motion> allMotions = new ArrayList<>();
    List<Motion> storedMotions = animation.get(shape);
    for (Motion m : storedMotions) {
      // make a copy of the motion
      allMotions.add(new Motion(m));
    }
    return allMotions;
  }

  /**
   * Implementation of the animation builder interface. This class will be the interconnection
   * between AnimationReader input and the animation. This class will create shape, add motion based
   * on what the input file specifies.
   */
  public static final class Builder implements AnimationBuilder<IModel> {

    // Need a field that represents the animation builder.
    private final IModel m = new AnimationModel();

    @Override
    public IModel build() {
      m.sortAndCheckListsOfMotions();
      return m;
    }

    @Override
    public AnimationBuilder<IModel> setBounds(int x, int y, int width, int height) {
      if (x < 0) {
        x = 0;
      }
      if (y < 0) {
        y = 0;
      }
      m.setCanvas(x, y, width, height);
      return this;
    }

    @Override
    public AnimationBuilder<IModel> declareShape(String name, String type) {
      m.createShape(type, name);
      return this;
    }

    @Override
    public AnimationBuilder<IModel> addMotion(String name, int t1, int x1, int y1, int w1, int h1,
                                              int r1, int g1, int b1, int t2, int x2, int y2,
                                              int w2, int h2, int r2, int g2, int b2) {
      m.addMotion(name, t1, x1, y1, w1, h1, r1, g1, b1, t2, x2, y2, w2, h2, r2, g2, b2);
      return this;
    }

    @Override
    public AnimationBuilder<IModel> addKeyframe(String name, int t, int x, int y, int w, int h,
                                                int r, int g, int b) {
      throw new UnsupportedOperationException("");
    }
  }

}
