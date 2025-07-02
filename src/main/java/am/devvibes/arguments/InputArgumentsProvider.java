package am.devvibes.arguments;

import lombok.experimental.UtilityClass;

@UtilityClass
public class InputArgumentsProvider {

  public static InputArgument getInputArguments(String[] args) {
    if (args.length != 2) {
      System.out.println("Usage: java -jar <your-jar-file> --config:<config-file-path> --betting-amount:<amount>");
      return null;
    }

    String configFilePath = null;
    Double betAmount = null;

    for (String arg : args) {
      if (arg.startsWith("--config:")) {
        configFilePath = arg.substring("--config:".length()).trim();
      } else if (arg.startsWith("--betting-amount:")) {
        String amountStr = arg.substring("--betting-amount:".length()).trim();
        try {
          betAmount = Double.parseDouble(amountStr);
          if (betAmount <= 0) {
            System.err.println("Error: Betting amount must be greater than zero.");
            return null;
          }
        } catch (NumberFormatException e) {
          System.err.println("Error: Invalid value for --betting-amount. Please provide a valid number.");
          return null;
        }
      } else {
        System.err.println("Error: Unknown argument \"" + arg + "\"");
        return null;
      }
    }
    if (configFilePath == null || betAmount == null) {
      System.err.println("Error: Both --config and --betting-amount arguments are required.");
      return null;
    }
    return new InputArgument(configFilePath, betAmount);
  }
}
