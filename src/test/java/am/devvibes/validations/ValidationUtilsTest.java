package am.devvibes.validations;

import am.devvibes.configs.Config;
import am.devvibes.configs.Probabilities;
import am.devvibes.configs.SymbolProbability;
import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class ValidationUtilsTest {

    @Test
    void testValidConfig() {
        // Create a valid Config object
        Config config = new Config();
        config.setColumns(3);
        config.setRows(3);
        config.setProbabilities(new Probabilities(
          Collections.singletonList(new SymbolProbability(0, 0, Collections.singletonMap("A", 1))),
          null
        ));

        // Call validateConfig with valid Config
        assertDoesNotThrow(() -> ValidationUtils.validateConfig(config));
    }

    @Test
    void testInvalidMatrixDimensions_ZeroOrNegative() {
        // Create an invalid Config with zero columns/rows
        Config config = new Config();
        config.setColumns(0);
        config.setRows(0);
        config.setProbabilities(new Probabilities(
          Collections.singletonList(new SymbolProbability(0, 0, Collections.singletonMap("A", 1))),
          null
        ));


        // Validate and ensure exception is thrown
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateConfig(config));
        assertEquals("Invalid matrix dimensions in the configuration file!", exception.getMessage());
    }

    @Test
    void testMissingProbabilities() {
        // Create a Config object missing probabilities
        Config config = new Config();
        config.setColumns(3);
        config.setRows(3);
        config.setProbabilities(null); // No probabilities

        // Validate and ensure exception is thrown
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateConfig(config));
        assertEquals("Missing standard symbol probabilities in the configuration file!", exception.getMessage());
    }

    @Test
    void testEmptyStandardSymbolsProbabilities() {
        // Create a Config object with empty standard symbols probabilities
        Config config = new Config();
        config.setColumns(3);
        config.setRows(3);
        config.setProbabilities(new Probabilities(Collections.emptyList(),
          null
        ));

        // Validate and ensure exception is thrown
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> ValidationUtils.validateConfig(config));
        assertEquals("Missing standard symbol probabilities in the configuration file!", exception.getMessage());
    }
}