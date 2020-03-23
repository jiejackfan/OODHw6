package cs3500.animator.controller;

import cs3500.animator.model.IModel;
import cs3500.animator.view.IView;
import cs3500.animator.view.SVGView;
import cs3500.animator.view.SwingView;
import cs3500.animator.view.TextView;
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
      if (m.getCurrentTick() < m.getMaxTick() - 1) {
        currentTick = currentTick + 1;
        m.setTick(currentTick);
      }
      v.refresh();
    }
  };

  public AnimationController(IView v, IModel m) {
    this.v = v;
    this.m = m;
  }

  @Override
  public void playAnimation() {
    // check instanceof
    if (v instanceof SwingView) {
      v.render();
      timer = new Timer(delay, taskPerformer);
      timer.setRepeats(true);
      timer.start();
    } else if (v instanceof TextView || v instanceof SVGView) {
      v.render();
    }
  }

  @Override
  public void setDelay(double tickPerSecond) {
    delay = (int) (1000 / tickPerSecond); // convert to ms per tick
  }

}
