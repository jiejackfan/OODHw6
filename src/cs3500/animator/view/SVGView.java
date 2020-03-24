package cs3500.animator.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import cs3500.animator.model.ReadOnlyModel;

public class SVGView implements IView {

  private String outputFileName;
  private final ReadOnlyModel readOnlyModel;

  public SVGView(ReadOnlyModel m) {
    this.readOnlyModel = m;
  }

  // no need to refresh,
  @Override
  public void refresh() {
    throw new UnsupportedOperationException("");
  }

  @Override
  public void render() {
    File file = new File(outputFileName);
    // Create the output file first if it does not exist
    String svgContent = null;
    if (!file.exists()) {
      try {
        file.createNewFile();
      } catch (IOException ioe) {
        throw new IllegalStateException("Output file creation failed.");
      }
    }
    // Write to the text file
    try {
      FileWriter fileWriter = new FileWriter(outputFileName);
      svgContent = svgContent +
              String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\" "
                              + "xmlns=\"http://www.w3.org/2000/svg\">",
                      Integer.toString(readOnlyModel.getCanvasWidth()),
                      Integer.toString(readOnlyModel.getCanvasHeight()));


      fileWriter.write(readOnlyModel.toString());
      fileWriter.close();
    } catch (IOException ioe) {
      throw new IllegalStateException("Failed to write to the output file.");
    }
  }

  @Override
  public void setOutputFileName(String outputFileName) {
    this.outputFileName = outputFileName;
  }

}
