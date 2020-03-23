package cs3500.animator.controller;

import cs3500.animator.model.IModel;
import cs3500.animator.model.ReadOnlyModel;
import cs3500.animator.view.IView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;


/**
 * Controller implementation.
 */
public class AnimationController implements IController {
  private IView v;
  private IModel m;

  Timer timer;
  int currentTick = 0;

  public static int delay;

  ActionListener taskPerformer = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {
      currentTick++;

    }
  };

  public AnimationController(IView v, IModel m) {
    this.v = v;
    this.m = m;
  }

  @Override
  public void playAnimation() {
    v.render();
    timer = new Timer(delay, taskPerformer);
    timer.start();
  }

  @Override
  public void setDelay(double tickPerSecond) {
    delay = (int) (1000 / tickPerSecond); // msec per tick
  }


}
