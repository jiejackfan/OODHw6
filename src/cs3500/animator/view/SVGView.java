package cs3500.animator.view;

import cs3500.animator.model.ReadOnlyModel;

public class SVGView implements IView {

  private String outputFileName;

  public SVGView(ReadOnlyModel m) {

  }

  // no need to refresh,
  @Override
  public void refresh() {
    throw new UnsupportedOperationException("");
  }

  @Override
  public void render() {

  }

  @Override
  public void setOutputFileName(String outputFileName) {
    this.outputFileName = outputFileName;
  }

}
