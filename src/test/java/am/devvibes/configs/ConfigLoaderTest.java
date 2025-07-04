package am.devvibes.configs;

import static am.devvibes.gameutils.GameUtils.objectMapper;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ConfigLoaderTest {

  @Test
  void testLoadConfig_successful() throws IOException {
    // Create a sample valid JSON configuration file
    File tempConfigFile = File.createTempFile("valid-config", ".json");
    String validConfigJson = """
          {
              "columns": 3,
              "rows": 3,
              "symbols": {
                  "A": { "reward_multiplier": 5, "type": "standard" },
                  "B": { "reward_multiplier": 3, "type": "standard" }
              },
              "probabilities": {
                  "standard_symbols": [
                      {
                          "column": 0,
                          "row": 0,
                          "symbols": { "A": 1, "B": 2 }
                      }
                  ],
                  "bonus_symbols": {
                      "symbols": { "10x": 1, "MISS": 2 }
                  }
              },
              "win_combinations": {
                  "same_symbol_3_times": {
                      "reward_multiplier": 1,
                      "when": "same_symbols",
                      "count": 3,
                      "group": "same_symbols"
                  }
              }
          }
      """;
    // Write JSON to the temp file
    objectMapper.writeValue(tempConfigFile, objectMapper.readTree(validConfigJson));

    // Act: Load the configuration
    Config config = ConfigLoader.loadConfig(tempConfigFile.getAbsolutePath());

    // Assert: Verify the loaded configuration
    assertNotNull(config);
    assertEquals(3, config.getColumns());
    assertEquals(3, config.getRows());
    assertEquals(2, config.getSymbols().size()); // A and B symbols
    assertEquals(1, config.getProbabilities().getStandardSymbols().size());
  }

  @Test
  void testLoadConfig_invalidFileThrowsException() {
    // Act & Assert: Loading from an invalid file should throw an exception
    IllegalStateException exception = assertThrows(
      IllegalStateException.class,
      () -> ConfigLoader.loadConfig("non-existent-file.json")
    );

    // Assert: Verify the exception message contains relevant information
    assertTrue(exception.getMessage().contains("Failed to load configuration file."));
  }

  @Test
  void testLoadConfig_malformedJSONThrowsException(@TempDir Path tempDir) throws IOException {
    // Create a sample malformed JSON configuration file
    File malformedConfigFile = tempDir.resolve("malformed-config.json").toFile();
    String malformedJson = """
          { "columns": 3, "rows": "INVALID_VALUE"
      """; // Missing closing brace and wrong data type for rows
    objectMapper.writeValue(malformedConfigFile, malformedJson);

    // Act & Assert: Loading malformed JSON should throw an exception
    IllegalStateException exception = assertThrows(
      IllegalStateException.class,
      () -> ConfigLoader.loadConfig(malformedConfigFile.getAbsolutePath())
    );

    // Assert: Verify exception message
    assertTrue(exception.getMessage().contains("Failed to load configuration file."));
  }
}