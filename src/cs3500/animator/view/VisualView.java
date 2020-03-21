package cs3500.animator.view;

public interface VisualView extends IView{
  /**
   * Refresh view to reflect change in the game state.
   */
  void refresh();

  /**
   * Make the view visible when starting the animation.
   */
  void makeVisible();
}
