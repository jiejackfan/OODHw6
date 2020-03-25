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
    String svgContent = "";
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
      List<IShape> initialShapes = readOnlyModel.getAnimation(1);
      List<IShape> allShapes = readOnlyModel.getAllShapes();
      int counter = 0;
      svgContent += String.format("<svg width=\"%d\" height=\"%d\" version=\"1.1\" "
                      + "xmlns=\"http://www.w3.org/2000/svg\">\n",
              readOnlyModel.getCanvasWidth(),
              readOnlyModel.getCanvasHeight());
      // Add the animation shape by insertion order
      for (IShape s : allShapes) {
        if (!readOnlyModel.getAllMotionsOfShape(s).isEmpty()) {
          switch (s.getShape()) {
            case rectangle:
              svgContent += String.format("\t<rect id=\"%s\" x=\"%.3f\" y=\"%.3f\" width=\"%.3f\" "
                              + "height=\"%.3f\" fill=\"rgb(%d,%d,%d)\" visibility=\"visible\">\n",
                      initialShapes.get(counter).getName(),
                      initialShapes.get(counter).getPosition().getX(),
                      initialShapes.get(counter).getPosition().getY(),
                      initialShapes.get(counter).getWidth(),
                      initialShapes.get(counter).getHeight(),
                      initialShapes.get(counter).getColor().getRed(),
                      initialShapes.get(counter).getColor().getGreen(),
                      initialShapes.get(counter).getColor().getBlue());
              svgContent = getShapeAnimationDetail(s, svgContent) + "\t</rect>\n";
              break;
            case oval:
              svgContent += String.format("\t<ellipse id=\"%s\" cx=\"%.3f\" cy=\"%.3f\" rx=\"%.3f\" "
                              + "ry=\"%.3f\" fill=\"rgb(%d,%d,%d)\" visibility=\"visible\">\n",
                      initialShapes.get(counter).getName(),
                      initialShapes.get(counter).getPosition().getX(),
                      initialShapes.get(counter).getPosition().getY(),
                      initialShapes.get(counter).getWidth(),
                      initialShapes.get(counter).getHeight(),
                      initialShapes.get(counter).getColor().getRed(),
                      initialShapes.get(counter).getColor().getGreen(),
                      initialShapes.get(counter).getColor().getBlue());
              svgContent = getShapeAnimationDetail(s, svgContent) + "\t</ellipse>\n";
              break;
            default:
              throw new IllegalArgumentException("Please provide a valid shape");
          }
        }
        counter++;
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
        svgContent += String.format("\t\t<animate attributeType=\"xml\" begin=\"%ds\" dur=\"%ds\" "
                        + "attributeName=\"%s\" from=\"%.3f\" to=\"%.3f\" fill=\"freeze\"/>\n",
                startTime, duration, x, m.getStartPosition().getX(),
                m.getEndPosition().getX());
      }
      if (m.getStartPosition().getY() != m.getEndPosition().getY()) {
        String y = s.getShape().equals("rectangle") ? "y" : "cy";
        svgContent += String.format("\t\t<animate attributeType=\"xml\" begin=\"%ds\" dur=\"%ds\" "
                        + "attributeName=\"%s\" from=\"%.3f\" to=\"%.3f\" fill=\"freeze\"/>\n",
                startTime, duration, y, m.getStartPosition().getY(), m.getEndPosition().getY());
      }
      if (m.getStartWidth() != m.getEndWidth()) {
        String w = s.getShape().equals("rectangle") ? "width" : "rx";
        svgContent += String.format("\t\t<animate attributeType=\"xml\" begin=\"%ds\" dur=\"%ds\" "
                        + "attributeName=\"%s\" from=\"%.3f\" to=\"%.3f\" fill=\"freeze\"/>\n",
                startTime, duration, w, m.getStartWidth(), m.getEndWidth());
      }
      if (m.getStartHeight() != m.getEndHeight()) {
        String h = s.getShape().equals("rectangle") ? "height" : "ry";
        svgContent += String.format("\t\t<animate attributeType=\"xml\" begin=\"%ds\" dur=\"%ds\" "
                        + "attributeName=\"%s\" from=\"%.3f\" to=\"%.3f\" fill=\"freeze\"/>\n",
                startTime, duration, h, m.getStartHeight(), m.getEndHeight());
      }
      if (!m.getStartColor().equals(m.getEndColor())) {
        svgContent += String.format("\t\t<animate attributeType=\"xml\" begin=\"%ds\" dur=\"%ds\" "
                        + "attributeName=\"fill\" from=\"rgb(%d,%d,%d)\" to=\"rgb(%d,%d,%d)\""
                        + " fill=\"freeze\"/>\n",
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
