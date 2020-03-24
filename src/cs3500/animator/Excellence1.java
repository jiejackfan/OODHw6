package cs3500.animator;

public class Excellence1 {
  public void main(String[] args) {
    double tickPerSecond = 1;
    String inputFileName = "";
    String outputFileName = "System.out";
    String viewType = "";

    int i = 2, j;
    String arg;

    while (i < args.length) {
      arg = args[i];

      if (arg.equals("-in")) {
      }
      else if (arg.equals("-view")) {
      }
      else if (arg.equals("-out")) {
      }
      else if (arg.equals("-speed")) {
        arg = args[++i];

      }

      i++;
    }

  }
}
