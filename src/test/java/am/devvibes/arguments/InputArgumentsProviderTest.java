package am.devvibes.arguments;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

class InputArgumentsProviderTest {

  @Test
  void testValidInputArguments() {
    // Valid arguments
    String[] args = {"--config:config.json", "--betting-amount:500"};

    // Call method
    InputArgument result = InputArgumentsProvider.getInputArguments(args);

    // Assertions
    assertNotNull(result);
    assertEquals("config.json", result.getConfigFilePath());
    assertEquals(500.0, result.getBetAmount());
  }

  @Test
  void testMissingArguments() {
    // Missing arguments
    String[] args = {};

    // Call method
    InputArgument result = InputArgumentsProvider.getInputArguments(args);

    // Assertions
    assertNull(result);
  }

  @Test
  void testBettingAmountBelowZero() {
    // Betting amount below zero
    String[] args = {"--config:config.json", "--betting-amount:-100"};

    // Call method
    InputArgument result = InputArgumentsProvider.getInputArguments(args);

    // Assertions
    assertNull(result);
  }

  @Test
  void testInvalidBettingAmount() {
    // Invalid betting amount format
    String[] args = {"--config:config.json", "--betting-amount:abc"};

    // Call method
    InputArgument result = InputArgumentsProvider.getInputArguments(args);

    // Assertions
    assertNull(result);
  }

  @Test
  void testMissingBothArguments() {
    // Missing both config and betting amount
    String[] args = {"--config:config.json"};

    // Call method
    InputArgument result = InputArgumentsProvider.getInputArguments(args);

    // Assertions
    assertNull(result);
  }

  @Test
  void testUnknownArgument() {
    // Unknown argument
    String[] args = {"--unknown:config.json", "--betting-amount:500"};

    // Call method
    InputArgument result = InputArgumentsProvider.getInputArguments(args);

    // Assertions
    assertNull(result);
  }
}