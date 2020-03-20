package cs3500.animator.controller;

import cs3500.animator.model.ReadOnlyModel;
import cs3500.animator.view.IView;

/**
 * Controller implementation.
 */
public class AnimationController implements IController {
  private IView v;
  private ReadOnlyModel m;

  public AnimationController(IView v){
    this.v = v;
  }

  @Override
  public void playAnimation(ReadOnlyModel m) {
    this.m = m;
  }
}
