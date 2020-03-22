package cs3500.animator.controller;

/**
 * Represents a controlller for animation. Links the view and model together so view can get access
 *  to reading a model and not mutate it.
 */
public interface IController {

  /**
   * Execute animation.
   */
  void playAnimation();

  void setDelay(int tickPerSecond);
}
