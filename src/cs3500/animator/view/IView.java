package cs3500.animator.view;



/**
 * View for animation: display the animation panel and provide visual to the user.
 */
public interface IView {

  /**
   * This function will be used in main. It will parse a string given by the input argument and
   *  return a view class of the type that the string specified.
   * @param viewType A string to represent which type of view the user wants to construct.
   * @return A view of the viewType that user have provided.
   */
  public IView parseViewTypeFromString(String viewType);

}
