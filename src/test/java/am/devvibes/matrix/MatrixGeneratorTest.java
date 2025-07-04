package am.devvibes.matrix;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import am.devvibes.configs.BonusSymbolProbabilities;
import am.devvibes.configs.Config;
import am.devvibes.configs.Probabilities;
import am.devvibes.configs.SymbolProbability;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class MatrixGeneratorTest {

  @Test
  void testGenerateMatrix() {
    // Mock Config
    Config mockConfig = mock(Config.class);

    // Define rows and columns for matrix
    when(mockConfig.getRows()).thenReturn(3); // Matrix: 3x3
    when(mockConfig.getColumns()).thenReturn(3);

    // Define symbol probabilities
    SymbolProbability symbolProbability = new SymbolProbability();
    symbolProbability.setRow(0);
    symbolProbability.setColumn(0);
    symbolProbability.setSymbols(Map.of("A", 1, "B", 2, "C", 1));
    Probabilities probabilities = new Probabilities(
      List.of(symbolProbability), // Standard symbol probabilities
      new BonusSymbolProbabilities(Map.of("+500", 1, "10x", 1)) // Bonus symbol probabilities
    );
    when(mockConfig.getProbabilities()).thenReturn(probabilities);

    // Generate matrix
    String[][] matrix = MatrixGenerator.generateMatrix(mockConfig);

    // Validate matrix contains symbols from the probabilities
    assertNotNull(matrix);
    assertEquals(3, matrix.length);
    assertEquals(3, matrix[0].length); // Validate size: 3x3

    // Validate at least one bonus symbol is applied
    boolean bonusSymbolApplied = false;
    for (String[] row : matrix) {
      for (String cell : row) {
        if (cell.equals("+500") || cell.equals("10x")) {
          bonusSymbolApplied = true;
          break;
        }
      }
    }

    assertTrue(bonusSymbolApplied, "At least one bonus symbol should be applied to the matrix.");
  }

  @Test
  void testPrintMatrixSeparately() {
    String[][] matrix = {
      {"A", "B", "C"},
      {"C", "+500", "A"},
      {"D", "E", "F"}
    };

    MatrixGenerator.printMatrixSeparately(matrix);
  }

  @Test
  void testPrintGameOverview() {
    // Define test data
    double reward = 1000;
    double betAmount = 500;
    Map<String, List<String>> appliedWinningCombinations = Map.of(
      "A", List.of("same_symbol_3_times"),
      "B", List.of("same_symbol_horizontally")
    );
    List<String> appliedBonusSymbols = List.of("+500", "10x");

    // Print the JSON overview
    assertDoesNotThrow(() -> MatrixGenerator.printGameOverview(
      reward, appliedWinningCombinations, appliedBonusSymbols, betAmount
    ));
  }

  @Test
  void testGenerateSymbol() {
    // Define symbol probabilities
    Map<String, Integer> probabilities = Map.of("A", 1, "B", 2, "C", 1);

    // Generate symbol based on probabilities
    String symbol = MatrixGenerator.generateSymbol(probabilities);

    // Validate the symbol is one of the defined keys
    assertTrue(probabilities.containsKey(symbol));
  }
}