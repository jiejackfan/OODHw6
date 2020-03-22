package cs3500.animator.view;

import cs3500.animator.model.ReadOnlyModel;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class AnimatorPanel extends JPanel {
  private ReadOnlyModel m;

  /**
   * Public constructor used to initialize the panel. Gives the panel access to a model so it can
   *  display the animation currently stored in the model.
   * @param m a read only model that has the animation.
   */
  public AnimatorPanel(ReadOnlyModel m) {
    this.m = m;
  }

  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
  }

}
