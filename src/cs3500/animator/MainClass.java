package cs3500.animator;

import cs3500.animator.controller.AnimationController;
import cs3500.animator.controller.IController;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.IModel;
import cs3500.animator.view.IView;
import cs3500.animator.view.SwingView;

/**
 * Used to test Swing View. This main function will create 2 shapes randomly. The view will then
 * play some motion of the shapes.
 */
public class MainClass {
  /**
   * The main method that executes the program.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {
    IModel m = new AnimationModel();
    m.createShape("rectangle", "r1");
    m.createShape("oval", "o1");
    m.addMotion("r1", 1, 20, 20, 100, 50, 255,
            255, 0, 10, 50, 50, 50, 50, 255, 0, 0);
    m.addMotion("o1", 5, 50, 10, 20, 20, 0, 0, 255,
            20, 50, 50, 10, 30, 0, 255, 0);

    IView v = new SwingView(m, 400, 400, 50, 50);

    IController c = new AnimationController(v, m);
    c.setDelay(1.5);
    c.playAnimation();
  }
}
