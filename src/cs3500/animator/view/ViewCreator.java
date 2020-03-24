package cs3500.animator.view;

import cs3500.animator.model.ReadOnlyModel;

/**
 * Factory class to create a view based on input arguments from cs3500.animator.main. This can create 3 different
 * types of view: svg, text, and swing (visual) view.
 */
public class ViewCreator {

  public enum viewType {
    text, svg, visual;
  }

  public ViewCreator() {
    //
  }

  public IView createViewBasedOnType(String type, ReadOnlyModel m, int width, int height, int x,
      int y) {
    if (viewType.visual == viewType.valueOf(type.toLowerCase())) {
      return new SwingView(m, width, height, x, y);
    } else if (viewType.text == viewType.valueOf(type.toLowerCase())) {
      return new TextView(m);
    } else if (viewType.svg == viewType.valueOf(type.toLowerCase())) {
      return new SVGView(m, width, height, x, y);
    }
    throw new IllegalStateException("Can't create the view specified in the input argument.");
  }
}
