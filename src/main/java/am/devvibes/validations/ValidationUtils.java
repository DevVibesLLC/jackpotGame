package am.devvibes.validations;

import am.devvibes.configs.Config;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ValidationUtils {

  public static void validateConfig(Config config) throws IllegalArgumentException {
    if (config.getColumns() <= 0 || config.getRows() <= 0) {
      throw new IllegalArgumentException("Invalid matrix dimensions in the configuration file!");
    }

    if (config.getProbabilities() == null || config.getProbabilities().getStandardSymbols().isEmpty()) {
      throw new IllegalArgumentException("Missing standard symbol probabilities in the configuration file!");
    }
  }
}
