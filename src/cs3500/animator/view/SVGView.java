package cs3500.animator.view;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import cs3500.animator.model.IShape;
import cs3500.animator.model.Motion;
import cs3500.animator.model.ReadOnlyModel;

public class SVGView implements IView {

  private String outputFileName;
  private final ReadOnlyModel readOnlyModel;

  public SVGView(ReadOnlyModel m, int width, int height, int x, int y) {
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
      List<IShape> allShapes = readOnlyModel.getAnimation(0);
      svgContent = svgContent +
              String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\" "
                              + "xmlns=\"http://www.w3.org/2000/svg\">",
                      Integer.toString(readOnlyModel.getCanvasWidth()),
                      Integer.toString(readOnlyModel.getCanvasHeight()));
      // Add the animation shape by insertion order
      for (IShape s : allShapes) {
        if (!readOnlyModel.getAllMotionsOfShape(s).isEmpty()) {
          switch (s.getShape()) {
            case rectangle:
              svgContent = svgContent
                      + String.format("<rect id=\"%s\" x=\"%.3f\" y=\"%.3f\" width=\"%.3f\" "
                              + "height=\"%.3f\" fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" >\n",
                      s.getName(), s.getPosition().getX(), s.getPosition().getY(),
                      s.getWidth(), s.getHeight(), s.getColor().getRed(), s.getColor().getGreen(),
                      s.getColor().getBlue())
                      + getShapeAnimationDetail(s)
                      + "</rect>";
              break;
            case oval:
              svgContent = svgContent
                      + String.format("<ellipse id=\"%s\" cx=\"%.3f\" cy=\"%.3f\" rx=\"%.3f\" "
                              + "ry=\"%.3f\" fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" >\n",
                      s.getName(), s.getPosition().getX(), s.getPosition().getY(),
                      s.getWidth(), s.getHeight(), s.getColor().getRed(), s.getColor().getGreen(),
                      s.getColor().getBlue())
                      + getShapeAnimationDetail(s)
                      + "</ellipse>";
              break;
            default:
              throw new IllegalArgumentException("Please provide a valid shape");
          }
        }
      }

      svgContent = svgContent + "</svg>";
      fileWriter.write(svgContent);
      fileWriter.close();
    } catch (IOException ioe) {
      throw new IllegalStateException("Failed to write to the output file.");
    }
  }

  private String getShapeAnimationDetail(IShape s) {
    // Generate SVG descriptions for all the changes in all motions of the given shape
    List<Motion> allMotions = readOnlyModel.getAllMotionsOfShape(s);
    for (Motion m : allMotions) {
      if (m.getStartPosition().getX() != m.getEndPosition().getX()) {
        // Need to consult TA
      }
    }
  }

  @Override
  public void setOutputFileName(String outputFileName) {
    this.outputFileName = outputFileName;
  }

}
