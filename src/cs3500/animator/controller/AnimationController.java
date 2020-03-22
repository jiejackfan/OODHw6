package cs3500.animator.controller;

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
  private ReadOnlyModel m;
  Timer timer;


  public static int delay;
  boolean delaySetSuccess = false;
  ActionListener taskPerformer = new ActionListener() {
    @Override
    public void actionPerformed(ActionEvent e) {

    }
  };

  public AnimationController(IView v, ReadOnlyModel m){
    this.v = v;
    this.m = m;
  }



  @Override
  public void playAnimation() {
    v.makeVisible();

    if (!delaySetSuccess) {
      throw new IllegalStateException("The tick speed is not set for the controller.");
    }

    timer = new Timer(delay, taskPerformer);
    timer.start();
  }

  @Override
  public void setDelay(int tickPerSecond) {
    delay = 1000 / tickPerSecond;
    delaySetSuccess = true;
  }

}
