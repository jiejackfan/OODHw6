package cs3500.animator;

import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.IModel;

/**
 * Used to test Swing View.
 */
public class mainClass {
  public static void main(String[] args) {
    IModel m = new AnimationModel();
    m.createShape("rectangle","r1");
    m.createShape("oval", "o1");
    m.addMotion("r1",1,20, 20, 100, 50, 255,
        255, 0, 10, 50, 50, 50, 50, 255, 0, 0);
    m.addMotion("o1", 1, );
  }
}
