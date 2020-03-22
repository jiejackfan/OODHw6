package cs3500.animator.controller;

import cs3500.animator.model.ReadOnlyModel;
import cs3500.animator.view.IView;

/**
 * Controller implementation.
 */
public class AnimationController implements IController {
  private IView v;
  private ReadOnlyModel m;

  public AnimationController(IView v, ReadOnlyModel m){
    this.v = v;
    this.m = m;
  }

  @Override
  public void playAnimation() {
    v.makeVisible();
  }
}
