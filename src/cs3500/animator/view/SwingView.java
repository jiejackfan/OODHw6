package cs3500.animator.view;

import cs3500.animator.model.ReadOnlyModel;
import javax.swing.JFrame;

public class SwingView extends JFrame implements IView {

  private AnimatorPanel p;


  /**
   * Empty constructor to create an empty swing view class for ViewCreator (factory) class.
   */
  public SwingView(ReadOnlyModel m) {

  }

  /**
   * Initializing constructor for the swing view class. Takes in the readonly model for shape
   *  information retrieval. Also takes in location and size information given by the input text
   *  to set up the canvas and window.
   * @param title name we want to give our pop up window.
   * @param m a read only model of our animation.
   * @param size1 the width of our canvas.
   * @param size2 the height of our canvas.
   * @param loc1 the leftmost x position of our pop up window.
   * @param loc2 the top most y position of our pop up window.
   */
  public SwingView(String title, ReadOnlyModel m, int size1, int size2, int loc1, int loc2) {
    super(title);
    setSize(size1, size2);
    setLocation(loc1, loc2);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    p = new AnimatorPanel(m);
    this.add(p);
  }

  @Override
  public void refresh() {
    this.repaint();
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void render() {
    makeVisible();
  }

  @Override
  public void setOutputFileName(String outputFileName) {
    //
  }
}
