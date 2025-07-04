package am.devvibes.arguments;

import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class InputArgumentsProvider {

  private static final Logger log = LoggerFactory.getLogger(InputArgumentsProvider.class);
  public static InputArgument getInputArguments(String[] args) {
    if (args.length != 2) {
      log.debug("Usage: java -jar <your-jar-file> --config:<config-file-path> --betting-amount:<amount>");
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
            log.error("Error: Betting amount must be greater than zero.");
            System.exit(0);
          }
        } catch (NumberFormatException e) {
          log.error("Error: Invalid value for --betting-amount. Please provide a valid number.");
          return null;
        }
      } else {
        log.error("Error: Unknown argument {}", arg);
        return null;
      }
    }
    if (configFilePath == null || betAmount == null) {
      log.error("Error: Both --config and --betting-amount arguments are required.");
      return null;
    }
    return new InputArgument(configFilePath, betAmount);
  }
}
