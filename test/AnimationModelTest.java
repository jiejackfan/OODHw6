import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.DifferentShapes;
import cs3500.animator.model.IModel;
import cs3500.animator.model.IShape;
import cs3500.animator.model.Position2D;
import cs3500.animator.model.Shape;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

/**
 * Test class for our animation model. Verify that our model can create shapes and add each shape's
 * list of motions. Also verify the model can remove shape and list of motions. Lastly, the test
 * should prove the model can correctly print out a string version of the entire animation and can
 * create a list of shapes that contains shapes with its characteristics at a certain time.
 */
public class AnimationModelTest {

  IModel animationOne = new AnimationModel();

  /**
   * createShape(shape, name) Invalid: shape = null/"", name = null/"" valid: create one given
   * shape, and create more than one shapes.
   */
  @Test(expected = IllegalArgumentException.class)
  public void testCreateShapeNull() {
    animationOne.createShape(null, "R");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateShapeEmptyString() {
    animationOne.createShape("", "R");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateShapeNameNull() {
    animationOne.createShape("rectangle", null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateShapeNameEmptyString() {
    animationOne.createShape("rectangle", "");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateShapeNotExist() {
    animationOne.createShape("triangle", "T");
  }

  @Test
  public void testShapesNoMotionToString() {
    animationOne.createShape("rectangle", "R");
    animationOne.createShape("oval", "O");
    assertEquals("Shape R rectangle\nShape O oval\n", animationOne.toString());
  }

  /**
   * removeShape(name) invalid: name does not exist in the animation valid: name exist in the
   * animation.
   */

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRemoveEmpty() {
    animationOne.removeShape("C");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRemove() {
    animationOne.createShape("rectangle", "R");
    animationOne.createShape("oval", "O");
    animationOne.removeShape("C");
  }

  @Test
  public void testValidRemove() {
    animationOne.createShape("rectangle", "R");
    animationOne.createShape("oval", "O");
    animationOne.removeShape("R");
    animationOne.removeShape("O");
    assertEquals("", animationOne.toString());
  }

  /**
   * AddMotion(...)
   */

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionShapeNotCreated() {
    animationOne.addMotion("R", 1, 200, 200, 200,
            100.75, 0, 0, 0, 20,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidName() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("C", 1, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidStartTime() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 0, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidEndTime() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, -1,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionStartTimeGreaterThanEndTime() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 100, 200, 200, 50,
            100, 255, 0, 0, 20,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidStartX() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, -1, 200, 50,
            100, 255, 0, 0, 20,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidStartY() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 0, -100, 50,
            100, 255, 0, 0, 20,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidEndX() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 20,
            -200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidEndY() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 20,
            200, -200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidStartWidth() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 0.0,
            100, 255, 0, 0, 20,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidEndWidth() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            100, 255, 0, 0, 20,
            200, 200, -50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidStartHeight() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            -100.75, 255, 0, 0, 20,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidEndHeight() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            100.75, 255, 0, 0, 20,
            200, 200, 50, 0, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidStartColorRed1() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            100.75, 700, 0, 0, 20,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidStartColorRed2() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            100.75, -700, 0, 0, 20,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidStartColorGreen1() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            100.75, 0, -255, 0, 20,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidStartColorGreen2() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            100.75, 0, 256, 0, 20,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidStartColorBlue1() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            100.75, 0, 0, -1, 20,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidStartColorBlue2() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            100.75, 0, 0, 800, 20,
            200, 200, 50, 100, 255, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidEndColorRed1() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            100.75, 255, 0, 0, 20,
            200, 200, 50, 100, -1, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidEndColorRed2() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            100.75, 255, 0, 0, 20,
            200, 200, 50, 100, 700, 0, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidEndColorGreen1() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            100.75, 0, 255, 0, 20,
            200, 200, 50, 100, 255, -2, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidEndColorGreen2() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            100.75, 0, 255, 0, 20,
            200, 200, 50, 100, 255, 800, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidEndColorBlue1() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            100.75, 0, 0, 0, 20,
            200, 200, 50, 100, 255, 0, -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAddMotionInvalidEndColorBlue2() {
    animationOne.createShape("rectangle", "R");
    animationOne.addMotion("R", 1, 200, 200, 200,
            100.75, 0, 0, 0, 20,
            200, 200, 50, 100, 255, 0, 900);
  }

  @Test
  public void testShapesOrderedMotionsToString() {
    animationOne.createShape("rectangle", "R");
    animationOne.createShape("oval", "O");

    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 10, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("O", 6, 440, 70, 120,
            60, 0, 0, 255, 20,
            440, 70, 120, 60, 0, 0, 255);
    animationOne.addMotion("O", 20, 440, 70, 120,
            60, 0, 0, 255, 50,
            440, 250, 120, 60, 0, 0, 255);
    assertEquals("Shape R rectangle\n"
                    + "motion R 1 200.0 200.0 50.0 100.0 255 0 0 "
                    + "10 200.0 200.0 50.0 100.0 255 0 0\n"
                    + "motion R 10 200.0 200.0 50.0 100.0 255 0 0 "
                    + "50 300.0 300.0 50.0 100.0 255 0 0\n"
                    + "Shape O oval\n"
                    + "motion O 6 440.0 70.0 120.0 60.0 0 0 255 "
                    + "20 440.0 70.0 120.0 60.0 0 0 255\n"
                    + "motion O 20 440.0 70.0 120.0 60.0 0 0 255 "
                    + "50 440.0 250.0 120.0 60.0 0 0 255\n",
            animationOne.toString());
  }

  /**
   * The order is not correct.
   */
  @Test
  public void testSameShapeSameMotionsDifferentNames() {
    animationOne.createShape("rectangle", "R1");
    animationOne.createShape("oval", "O2");
    animationOne.createShape("oval", "O1");
    animationOne.createShape("rectangle", "R2");

    animationOne.addMotion("O2", 6, 440, 70, 120,
            60, 0, 0, 255, 20,
            440, 70, 120, 60, 0, 0, 255);
    animationOne.addMotion("O2", 20, 440, 70, 120,
            60, 0, 0, 255, 50,
            440, 250, 120, 60, 0, 0, 255);
    animationOne.addMotion("R1", 1, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.addMotion("R1", 10, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R2", 1, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.addMotion("R2", 10, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("O1", 6, 440, 70, 120,
            60, 0, 0, 255, 20,
            440, 70, 120, 60, 0, 0, 255);
    animationOne.addMotion("O1", 20, 440, 70, 120,
            60, 0, 0, 255, 50,
            440, 250, 120, 60, 0, 0, 255);

    assertEquals("Shape R1 rectangle\n"
                    + "motion R1 1 200.0 200.0 50.0 100.0 255 0 0 "
                    + "10 200.0 200.0 50.0 100.0 255 0 0\n"
                    + "motion R1 10 200.0 200.0 50.0 100.0 255 0 0 "
                    + "50 300.0 300.0 50.0 100.0 255 0 0\n"
                    + "Shape O2 oval\n"
                    + "motion O2 6 440.0 70.0 120.0 60.0 0 0 255 "
                    + "20 440.0 70.0 120.0 60.0 0 0 255\n"
                    + "motion O2 20 440.0 70.0 120.0 60.0 0 0 255 "
                    + "50 440.0 250.0 120.0 60.0 0 0 255\n"
                    + "Shape O1 oval\n"
                    + "motion O1 6 440.0 70.0 120.0 60.0 0 0 255 "
                    + "20 440.0 70.0 120.0 60.0 0 0 255\n"
                    + "motion O1 20 440.0 70.0 120.0 60.0 0 0 255 "
                    + "50 440.0 250.0 120.0 60.0 0 0 255\n"
                    + "Shape R2 rectangle\n"
                    + "motion R2 1 200.0 200.0 50.0 100.0 255 0 0 "
                    + "10 200.0 200.0 50.0 100.0 255 0 0\n"
                    + "motion R2 10 200.0 200.0 50.0 100.0 255 0 0 "
                    + "50 300.0 300.0 50.0 100.0 255 0 0\n",
            animationOne.toString());
  }

  @Test
  public void testShapesUnorderedMotionsToString() {
    animationOne.createShape("rectangle", "R");
    animationOne.createShape("oval", "O");

    animationOne.addMotion("O", 20, 440, 70, 120,
            60, 0, 0, 255, 50,
            440, 250, 120, 60, 0, 0, 255);
    animationOne.addMotion("R", 10, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.addMotion("O", 6, 440, 70, 120,
            60, 0, 0, 255, 20,
            440, 70, 120, 60, 0, 0, 255);

    assertEquals("Shape R rectangle\n"
                    + "motion R 1 200.0 200.0 50.0 100.0 255 0 0 "
                    + "10 200.0 200.0 50.0 100.0 255 0 0\n"
                    + "motion R 10 200.0 200.0 50.0 100.0 255 0 0 "
                    + "50 300.0 300.0 50.0 100.0 255 0 0\n"
                    + "Shape O oval\n"
                    + "motion O 6 440.0 70.0 120.0 60.0 0 0 255 "
                    + "20 440.0 70.0 120.0 60.0 0 0 255\n"
                    + "motion O 20 440.0 70.0 120.0 60.0 0 0 255 "
                    + "50 440.0 250.0 120.0 60.0 0 0 255\n",
            animationOne.toString());
  }

  /**
   * CheckValidAnimation(name) - this function is called in the public functions.
   */
  @Test
  public void testNoShape() {
    assertEquals("", animationOne.toString());
  }


  @Test
  public void testShapeNoMotion() {
    // Create shapes
    animationOne.createShape("rectangle", "R");
    animationOne.createShape("oval", "O");
    assertEquals("Shape R rectangle\nShape O oval\n", animationOne.toString());
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidAnimationTimeOverlap() {
    animationOne.createShape("rectangle", "R");
    // The first motion starts at 0 and ends at 20
    // The second motion starts at 10 and ends at 50
    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 20,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 10, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.toString();
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidAnimationUnorderedTimeTeleport() {
    animationOne.createShape("rectangle", "R");
    // The first motion starts at 40 and ends at 50
    // The second motion starts at 0 and ends at 20
    animationOne.addMotion("R", 40, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 20,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.toString();
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidAnimationStartDoesNotEqualToEndPosition() {
    animationOne.createShape("rectangle", "R");
    // The first motion ends with position (200,200)
    // The second motion starts with position (2,2)
    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 20,
            200, 200, 100, 100, 255, 0, 0);
    animationOne.addMotion("R", 20, 2, 2, 200,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.toString();
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidAnimationStartDoesNotEqualToEndWidth() {
    animationOne.createShape("rectangle", "R");
    // The first motion ends with width 200
    // The second motion starts with width 50
    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 20,
            200, 200, 200, 100, 255, 0, 0);
    animationOne.addMotion("R", 20, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.toString();
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidAnimationStartDoesNotEqualToEndHeight() {
    animationOne.createShape("rectangle", "R");
    // The first motion ends with height 100
    // The second motion starts with height 200
    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 20,
            200, 200, 100, 100, 255, 0, 0);
    animationOne.addMotion("R", 20, 200, 200, 200,
            200, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.toString();
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidAnimationStartDoesNotEqualToEndColor() {
    animationOne.createShape("rectangle", "R");
    // The first motion ends with color rgb(0,0,0)
    // The second motion starts with color rgb(3,5,4)
    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 20,
            200, 200, 100, 100, 0, 0, 0);
    animationOne.addMotion("R", 20, 200, 200, 200,
            100, 3, 5, 4, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.toString();
  }

  /**
   * getAnimation(time) invalid: time < 1 valid: get the current state of the animation.
   */

  @Test(expected = IllegalArgumentException.class)
  public void testGetAnimationAtInvalidTime() {
    animationOne.createShape("rectangle", "R");
    animationOne.createShape("oval", "O");

    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 10, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("O", 6, 440, 70, 120,
            60, 0, 0, 255, 20,
            440, 70, 120, 60, 0, 0, 255);
    animationOne.addMotion("O", 20, 440, 70, 120,
            60, 0, 0, 255, 50,
            440, 250, 120, 60, 0, 0, 255);
    animationOne.getAnimation(0);
  }

  @Test
  public void testShapeGetName() {
    IShape r = new Shape(new Color(0, 0, 0), new Position2D(0, 0), 10, 10, "R",
        DifferentShapes.rectangle);
    assertEquals("rectangle", r.getShapeName());
  }

  @Test
  public void testShapesHashCode() {
    IShape r1 = new Shape(new Color(0, 0, 0), new Position2D(0, 0), 10, 10, "R1",
        DifferentShapes.rectangle);
    IShape r2 = new Shape(new Color(0, 0, 0), new Position2D(0, 0), 10, 10, "R2",
        DifferentShapes.rectangle);
    assertFalse(r1.hashCode() == r2.hashCode());
  }

  @Test
  public void testShapesEqual() {
    IShape r1 = new Shape(new Color(0, 0, 0), new Position2D(0, 0), 10, 10, "R",
        DifferentShapes.rectangle);
    IShape r2 = new Shape(new Color(0, 0, 0), new Position2D(0, 0), 10, 10, "R",
        DifferentShapes.rectangle);
    assertTrue(r1.equals(r2));
  }

  @Test
  public void testShapesNotEqual() {
    IShape r1 = new Shape(new Color(0, 0, 0), new Position2D(0, 0), 10, 10, "R1",
        DifferentShapes.rectangle);
    IShape r2 = new Shape(new Color(0, 0, 0), new Position2D(0, 0), 10, 10, "R2",
        DifferentShapes.rectangle);
    assertFalse(r1.equals(r2));
  }

  @Test
  public void testGetAnimationAtValidAtTime1NoOval() {
    animationOne.createShape("rectangle", "R");
    animationOne.createShape("oval", "O");
    // R - from 1: at (0,0) size 10*10 with rgb(0,0,0)
    //     to 11: at (10,10) size 20*20 with rgb(100,100,100)
    animationOne.addMotion("R", 1, 0, 0, 10,
            10, 0, 0, 0, 11,
            10, 10, 20, 20, 100, 100, 100);
    // O - from 11: at (0,0) size 10*10 with rgb(0,0,0)
    //     to 21: at (10,10) size 20*20 with rgb(100,100,100)
    animationOne.addMotion("O", 21, 0, 0, 10,
            10, 0, 0, 0, 21,
            10, 10, 20, 20, 100, 100, 100);
    List<IShape> currentShape = new ArrayList<>();
    currentShape.add(
            new Shape(new Color(0, 0, 0), new Position2D(0, 0), 10, 10, "R",
                DifferentShapes.rectangle));
    assertEquals(currentShape.get(0), animationOne.getAnimation(1).get(0));
  }

  @Test
  public void testGetAnimationAtValidAtTime6OnlyRectangle() {
    animationOne.createShape("rectangle", "R");
    animationOne.createShape("oval", "O");
    // R - from 1: at (0,0) size 10*10 with rgb(0,0,0)
    //     to 11: at (10,10) size 20*20 with rgb(100,100,100)
    animationOne.addMotion("R", 1, 0, 0, 10,
            10, 0, 0, 0, 11,
            10, 10, 20, 20, 100, 100, 100);
    // O - from 11: at (0,0) size 10*10 with rgb(0,0,0)
    //     to 21: at (10,10) size 20*20 with rgb(100,100,100)
    animationOne.addMotion("O", 11, 0, 0, 10,
            10, 0, 0, 0, 21,
            10, 10, 20, 20, 100, 100, 100);
    List<IShape> currentShape = new ArrayList<>();
    currentShape.add(
            new Shape(new Color(50, 50, 50), new Position2D(5, 5), 15.0, 15.0, "R",
                DifferentShapes.rectangle));
    assertEquals(currentShape.get(0), animationOne.getAnimation(6).get(0));
  }

  @Test
  public void testGetAnimationAtValidAtTime11HaveBothShapes() {
    animationOne.createShape("rectangle", "R");
    animationOne.createShape("oval", "O");
    // R - from 1: at (0,0) size 10*10 with rgb(0,0,0)
    //     to 11: at (10,10) size 20*20 with rgb(100,100,100)
    animationOne.addMotion("R", 1, 0, 0, 10,
            10, 0, 0, 0, 11,
            10, 10, 20, 20, 100, 100, 100);
    // O - from 11: at (0,0) size 10*10 with rgb(0,0,0)
    //     to 21: at (10,10) size 20*20 with rgb(100,100,100)
    animationOne.addMotion("O", 11, 0, 0, 10,
            10, 0, 0, 0, 21,
            10, 10, 20, 20, 100, 100, 100);
    List<IShape> currentShape = new ArrayList<>();
    currentShape.add(
            new Shape(new Color(100, 100, 100), new Position2D(10, 10), 20, 20, "R",
                DifferentShapes.rectangle));
    currentShape.add(
            new Shape(new Color(0, 0, 0), new Position2D(0, 0), 10, 10, "O",
                DifferentShapes.oval));
    assertEquals(currentShape.get(0), animationOne.getAnimation(11).get(0));
    assertEquals(currentShape.get(1), animationOne.getAnimation(11).get(1));
  }

  @Test
  public void testGetAnimationAtValidAtTime21NoRectangle() {
    animationOne.createShape("rectangle", "R");
    animationOne.createShape("oval", "O");
    // R - from 1: at (0,0) size 10*10 with rgb(0,0,0)
    //     to 11: at (10,10) size 20*20 with rgb(100,100,100)
    animationOne.addMotion("R", 1, 0, 0, 10,
            10, 0, 0, 0, 11,
            10, 10, 20, 20, 100, 100, 100);
    // O - from 11: at (0,0) size 10*10 with rgb(0,0,0)
    //     to 21: at (10,10) size 20*20 with rgb(100,100,100)
    animationOne.addMotion("O", 11, 0, 0, 10,
            10, 0, 0, 0, 21,
            10, 10, 20, 20, 100, 100, 100);
    List<IShape> currentShape = new ArrayList<>();
    currentShape.add(
            new Shape(new Color(100, 100, 100), new Position2D(10, 10), 20, 20, "O",
                DifferentShapes.oval));
    assertEquals(currentShape.get(0), animationOne.getAnimation(21).get(0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRemoveMotionNameDoesNotExist() {
    animationOne.createShape("rectangle", "R");

    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 10, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 50, 300, 300, 50,
            100, 255, 0, 0, 51,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 51, 300, 300, 50,
            100, 255, 0, 0, 70,
            300, 300, 25, 100, 255, 0, 0);
    animationOne.removeMotion("O", 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRemoveMotionNameNull() {
    animationOne.createShape("rectangle", "R");

    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 10, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 50, 300, 300, 50,
            100, 255, 0, 0, 51,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 51, 300, 300, 50,
            100, 255, 0, 0, 70,
            300, 300, 25, 100, 255, 0, 0);
    animationOne.removeMotion(null, 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRemoveMotionNameEmpty() {
    animationOne.createShape("rectangle", "R");

    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 10, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 50, 300, 300, 50,
            100, 255, 0, 0, 51,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 51, 300, 300, 50,
            100, 255, 0, 0, 70,
            300, 300, 25, 100, 255, 0, 0);
    animationOne.removeMotion("", 0);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRemoveMotionIndexTooSmall() {
    animationOne.createShape("rectangle", "R");

    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 10, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 50, 300, 300, 50,
            100, 255, 0, 0, 51,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 51, 300, 300, 50,
            100, 255, 0, 0, 70,
            300, 300, 25, 100, 255, 0, 0);
    animationOne.removeMotion("R", -1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRemoveMotionIndexTooLarge() {
    animationOne.createShape("rectangle", "R");

    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 10, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 50, 300, 300, 50,
            100, 255, 0, 0, 51,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 51, 300, 300, 50,
            100, 255, 0, 0, 70,
            300, 300, 25, 100, 255, 0, 0);
    animationOne.removeMotion("R", 800);
  }

  @Test
  public void testRemoveFirstMotion() {
    animationOne.createShape("rectangle", "R");

    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 10, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 50, 300, 300, 50,
            100, 255, 0, 0, 51,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 51, 300, 300, 50,
            100, 255, 0, 0, 70,
            300, 300, 25, 100, 255, 0, 0);
    animationOne.removeMotion("R", 0);
    assertEquals("Shape R rectangle\n"
            + "motion R 10 200.0 200.0 50.0 100.0 255 0 0 "
            + "50 300.0 300.0 50.0 100.0 255 0 0\n"
            + "motion R 50 300.0 300.0 50.0 100.0 255 0 0 "
            + "51 300.0 300.0 50.0 100.0 255 0 0\n"
            + "motion R 51 300.0 300.0 50.0 100.0 255 0 0 "
            + "70 300.0 300.0 25.0 100.0 255 0 0\n", animationOne.toString());
  }

  @Test
  public void testRemoveLastMotion() {
    animationOne.createShape("rectangle", "R");

    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 10, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 50, 300, 300, 50,
            100, 255, 0, 0, 51,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 51, 300, 300, 50,
            100, 255, 0, 0, 70,
            300, 300, 25, 100, 255, 0, 0);
    animationOne.removeMotion("R", 3);
    assertEquals("Shape R rectangle\n"
                    + "motion R 1 200.0 200.0 50.0 100.0 255 0 0 "
                    + "10 200.0 200.0 50.0 100.0 255 0 0\n"
                    + "motion R 10 200.0 200.0 50.0 100.0 255 0 0 "
                    + "50 300.0 300.0 50.0 100.0 255 0 0\n"
                    + "motion R 50 300.0 300.0 50.0 100.0 255 0 0 "
                    + "51 300.0 300.0 50.0 100.0 255 0 0\n",
            animationOne.toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidRemoveMotionFromMiddle() {
    animationOne.createShape("rectangle", "R");

    animationOne.addMotion("R", 1, 200, 200, 50,
            100, 255, 0, 0, 10,
            200, 200, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 10, 200, 200, 50,
            100, 255, 0, 0, 50,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 50, 300, 300, 50,
            100, 255, 0, 0, 51,
            300, 300, 50, 100, 255, 0, 0);
    animationOne.addMotion("R", 51, 300, 300, 50,
            100, 255, 0, 0, 70,
            300, 300, 25, 100, 255, 0, 0);
    animationOne.removeMotion("R", 1);
  }

}
