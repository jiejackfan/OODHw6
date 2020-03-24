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
              svgContent += String.format("<rect id=\"%s\" x=\"%.3f\" y=\"%.3f\" width=\"%.3f\" "
                              + "height=\"%.3f\" fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" >\n",
                      s.getName(), s.getPosition().getX(), s.getPosition().getY(),
                      s.getWidth(), s.getHeight(), s.getColor().getRed(), s.getColor().getGreen(),
                      s.getColor().getBlue())
                      + getShapeAnimationDetail(s, svgContent)
                      + "</rect>";
              break;
            case oval:
              svgContent += String.format("<ellipse id=\"%s\" cx=\"%.3f\" cy=\"%.3f\" rx=\"%.3f\" "
                              + "ry=\"%.3f\" fill=\"rgb(%d,%d,%d)\" visibility=\"visible\" >\n",
                      s.getName(), s.getPosition().getX(), s.getPosition().getY(),
                      s.getWidth(), s.getHeight(), s.getColor().getRed(), s.getColor().getGreen(),
                      s.getColor().getBlue())
                      + getShapeAnimationDetail(s, svgContent)
                      + "</ellipse>";
              break;
            default:
              throw new IllegalArgumentException("Please provide a valid shape");
          }
        }
      }
      svgContent += "</svg>";
      fileWriter.write(svgContent);
      fileWriter.close();
    } catch (IOException ioe) {
      throw new IllegalStateException("Failed to write to the output file.");
    }
  }

  private String getShapeAnimationDetail(IShape s, String svgContent) {
    // Generate SVG descriptions for all the changes in all motions of the given shape
    List<Motion> allMotions = readOnlyModel.getAllMotionsOfShape(s);
    for (Motion m : allMotions) {
      int startTime = m.getStartTime();
      int duration = m.getEndTime() - m.getStartTime();
      if (m.getStartPosition().getX() != m.getEndPosition().getX()) {
        String x = s.getShape().equals("rectangle") ? "x" : "cx";
        svgContent += String.format("<animate attributeType=\"xml\" begin=\"%ds\" dur=\"%ds\" "
                        + "attributeName=\"%s\" from=\"%f.3\" to=\"%f.3\" fill=\"freeze\" />",
                startTime, duration, x, m.getStartPosition().getX(),
                m.getEndPosition().getX());
      } else if (m.getStartPosition().getY() != m.getEndPosition().getY()) {
        String y = s.getShape().equals("rectangle") ? "y" : "cy";
        svgContent += String.format("<animate attributeType=\"xml\" begin=\"%ds\" dur=\"%ds\" "
                        + "attributeName=\"%s\" from=\"%f.3\" to=\"%f.3\" fill=\"freeze\" />",
                startTime, duration, y, m.getStartPosition().getY(), m.getEndPosition().getY());
      } else if (m.getStartWidth() != m.getEndWidth()) {
        String w = s.getShape().equals("rectangle") ? "width" : "rx";
        svgContent += String.format("<animate attributeType=\"xml\" begin=\"%ds\" dur=\"%ds\" "
                        + "attributeName=\"%s\" from=\"%f.3\" to=\"%f.3\" fill=\"freeze\" />",
                startTime, duration, w, m.getStartWidth(), m.getEndWidth());
      } else if (m.getStartHeight() != m.getEndHeight()) {
        String h = s.getShape().equals("rectangle") ? "height" : "ry";
        svgContent += String.format("<animate attributeType=\"xml\" begin=\"%ds\" dur=\"%ds\" "
                        + "attributeName=\"%s\" from=\"%f.3\" to=\"%f.3\" fill=\"freeze\" />",
                startTime, duration, h, m.getStartHeight(), m.getEndHeight());
      } else if (!m.getStartColor().equals(m.getEndColor())) {
        svgContent += String.format("<animate attributeType=\"xml\" begin=\"%ds\" dur=\"%ds\" "
                        + "attributeName=\"fill\" from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\""
                        + " fill=\"freeze\" />",
                startTime, duration, m.getStartColor().getRed(), m.getStartColor().getGreen(),
                m.getStartColor().getBlue(), m.getEndColor().getRed(), m.getEndColor().getGreen(),
                m.getEndColor().getBlue());
      }
    }
    return svgContent;
  }

  @Override
  public void setOutputFileName(String outputFileName) {
    this.outputFileName = outputFileName;
  }

}
