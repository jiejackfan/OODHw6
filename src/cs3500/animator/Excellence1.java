package cs3500.animator;

import java.io.StringReader;

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

      // use this type of check for "wordy" arguments
      if (arg.equals("-in")) {
        System.out.println("verbose mode on");
        vflag = true;
      }
      // use this type of check for arguments that require arguments
      else if (arg.equals("-output")) {
      }

      i++;
    }

  }

  }
}
