package am.devvibes;

import static am.devvibes.arguments.InputArgumentsProvider.getInputArguments;

import am.devvibes.arguments.InputArgument;
import am.devvibes.config.Config;
import am.devvibes.config.ConfigLoader;

public class App {

  public static void main(String[] args) {
    InputArgument result = getInputArguments(args);

    try {
      Config config = ConfigLoader.loadConfig(result.getConfigFilePath());
      validateConfig(config);

      System.out.println("\nBet Amount: " + result.getBetAmount());

    } catch (Exception e) {
      System.err.println("An error occurred: " + e.getMessage());
      e.printStackTrace();
    }
  }

  private static void validateConfig(Config config) throws Exception {
    if (config.getColumns() <= 0 || config.getRows() <= 0) {
      throw new Exception("Invalid matrix dimensions in the configuration file!");
    }

    if (config.getProbabilities() == null || config.getProbabilities().getStandardSymbols().isEmpty()) {
      throw new Exception("Missing standard symbol probabilities in the configuration file!");
    }
  }
}
