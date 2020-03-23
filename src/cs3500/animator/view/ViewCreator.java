package cs3500.animator.view;

/**
 * Factory class to create a view based on input arguments from main. This can create 3 different
 * types of view: svg, text, and swing (visual) view.
 */
public class ViewCreator {

  public enum viewType {
    text, svg, visual;
  }

  public ViewCreator() {
    //
  }

  public IView createViewBasedOnType(String type) {
    if (viewType.visual == viewType.valueOf(type.toLowerCase())) {
      return new SwingView();
    } else if (viewType.text == viewType.valueOf(type.toLowerCase())) {
      return new TextView();
    }
    else if (viewType.svg == viewType.valueOf(type.toLowerCase())) {
      return new SVGView();
    }
    throw new IllegalStateException("Can't create the view specified in the input argument.");
  }
}
