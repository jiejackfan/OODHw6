package cs3500.animator.view;

/**
 * View for animation: display the animation panel and provide visual to the user.
 */
public interface IView {

  /**
   * Refresh view to reflect change in the game state.
   */
  void refresh();

  /**
   * Make the view visible when starting the animation.
   */
  void makeVisible();

  void render();
}
