package cs3500.animator.view;

/**
 * View for animation: display the animation panel and provide visual to the user. This class will
 *  take care of render (starting) the view, refreshing the view everytime controller updates its
 *  tick and also set output file name for each view.
 */
public interface IView {

  /**
   * Refresh view to reflect change in the game state.
   * @throws UnsupportedOperationException in text and SVG view because no animation will be played
   *  in those two classes live.
   */
  void refresh();

  /**
   * Called at the beginning of each view to start their animation building process.
   */
  void render();

  /**
   * Set the output file name for the text.
   * @param outputFileName String that you want to name the output file with.
   * @throws UnsupportedOperationException in the swing view class because swing view does not need
   *  an output name.
   */
  void setOutputFileName(String outputFileName);
}
