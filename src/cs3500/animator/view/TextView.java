package cs3500.animator.view;

import cs3500.animator.model.ReadOnlyModel;

public class TextView implements IView {
  private String outputFileName;

  public TextView(ReadOnlyModel m) {
    //
  }

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
