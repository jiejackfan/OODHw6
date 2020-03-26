package cs3500.animator;

import cs3500.animator.controller.AnimationController;
import cs3500.animator.controller.IController;
import cs3500.animator.model.AnimationModel;
import cs3500.animator.model.IModel;
import cs3500.animator.util.AnimationBuilder;
import cs3500.animator.util.AnimationReader;
import cs3500.animator.view.IView;
import cs3500.animator.view.ViewCreator;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

/**
 * Main class that is out of date. Will use ExcellenceCLI insteaed.
 */
public final class Excellence {
  /**
   * The main method that executes the program.
   *
   * @param args the input arguments
   */
  public static void main(String[] args) {

    Options options = new Options();

    Option in = new Option("in", true, "input animation file name");
    in.setRequired(true);
    options.addOption(in);

    Option view = new Option("view", true, "type of view");
    view.setRequired(true);
    options.addOption(view);

    Option out = new Option("out", true, "output file path");
    out.setRequired(false);
    options.addOption(out);

    Option speed = new Option("speed", true, "speed of animation");
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
    String outputFilePath = null;
    if (cmd.hasOption("out")) {
      outputFilePath = cmd.getOptionValue("out");
      System.out.println(outputFilePath); // debug
    }
    double animationSpeed;
    if (cmd.hasOption("speed")) {
      String rawSpeed = cmd.getOptionValue("speed");
      animationSpeed = Double.parseDouble(rawSpeed);
    } else {
      animationSpeed = 1.0;
    }

    AnimationBuilder<IModel> modelBuilder = new AnimationModel.Builder();

    IModel m;
    try {
      Readable inputFileContent = new StringReader(
              new String(Files.readAllBytes(Paths.get(inputFileName))));
      m = AnimationReader.parseFile(inputFileContent, modelBuilder);
    } catch (IOException e) {
      throw new IllegalArgumentException("The input file does not exist.");
    }

    IView v = new ViewCreator().createViewBasedOnType(viewType, m, m.getCanvasWidth(),
            m.getCanvasHeight(), m.getCanvasX(), m.getCanvasY());
    v.setOutputFileName(outputFilePath);
    IController c = new AnimationController(v, m);

    c.setDelay(animationSpeed);
    c.playAnimation();

  }
}
