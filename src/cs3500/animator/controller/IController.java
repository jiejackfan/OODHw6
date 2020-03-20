package cs3500.animator.controller;

import cs3500.animator.model.ReadOnlyModel;

/**
 *
 */
public interface IController {

  /**
   *
   * @param m a animation model.
   */
  void playAnimation(ReadOnlyModel m);
}
