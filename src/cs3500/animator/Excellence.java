package cs3500.animator;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.ParseException;

public final class Excellence {
  public static void main(String[] args) {

    Options options = new Options();

    Option in = new Option("in", true, "input animation file name");
    in.setRequired(true);
    options.addOption(in);

    Option view = new Option("view", true, "type of view");
    view.setRequired(true);
    options.addOption(view);

    Option out = new Option("out", false, "output file path");
    out.setRequired(false);
    options.addOption(out);

    Option speed = new Option("speed", false, "speed of animation");
    speed.setRequired(false);
    options.addOption(speed);

    CommandLineParser parser = new DefaultParser();
    HelpFormatter formatter = new HelpFormatter();
    CommandLine cmd = null;

    try {
      cmd = parser.parse(options, args);
    } catch (ParseException e) {
      System.out.println(e.getMessage());
      formatter.printHelp("Help Manual", options);
      System.exit(1);
    }

    String inputFileName = cmd.getOptionValue("in");
    String viewType = cmd.getOptionValue("view");
    if (cmd.hasOption("out")) {
      String outputFilePath = cmd.getOptionValue("out");
    }
    if (cmd.hasOption("speed")) {
      String rawSpeed = cmd.getOptionValue("speed");
      double animationSpeed = Double.parseDouble(rawSpeed);
    }

  }
}
