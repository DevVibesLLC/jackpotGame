package am.devvibes.winningcombination;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import am.devvibes.configs.Config;
import am.devvibes.configs.WinningCombination;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class WinningCombinationDetectorTest {

  @Test
  void testDetectSameSymbolCombination() {
    // Matrix with repeated symbols
    String[][] matrix = {
      {"A", "A", "B"},
      {"A", "C", "B"},
      {"A", "A", "A"}
    };

    // Mock Config
    Config mockConfig = mock(Config.class);

    // Define winning combinations
    Map<String, WinningCombination> winCombinations = new HashMap<>();
    winCombinations.put("same_symbol_5_times", new WinningCombination(
      1.0,
      "same_symbols",
      5,
      "group1",
      null
    ));
    when(mockConfig.getWinCombinations()).thenReturn(winCombinations);

    // Detect winning combinations
    Map<String, List<String>> result = WinningCombinationDetector.detectWinningCombinations(matrix, mockConfig);

    // Validate combinations detected for symbol "A"
    assertTrue(result.containsKey("A"));
    assertEquals(1, result.get("A").size());
    assertTrue(result.get("A").contains("same_symbol_5_times"));

    // Validate no combination detected for symbol "B"
    assertFalse(result.containsKey("B"));
  }

  @Test
  void testDetectLinearSymbolCombination_RowWin() {
    // Matrix with a horizontal row win
    String[][] matrix = {
      {"A", "A", "A"},
      {"B", "C", "B"},
      {"A", "A", "A"}
    };

    // Mock Config
    Config mockConfig = mock(Config.class);

    // Define winning combinations
    Map<String, WinningCombination> winCombinations = new HashMap<>();
    winCombinations.put("same_symbols_horizontal", new WinningCombination(
      2.0,
      "linear_symbols",
      0,
      "group_horizontal",
      List.of(
        List.of("0:0", "0:1", "0:2"), // First row
        List.of("2:0", "2:1", "2:2")  // Third row
      )
    ));
    when(mockConfig.getWinCombinations()).thenReturn(winCombinations);

    // Detect winning combinations
    Map<String, List<String>> result = WinningCombinationDetector.detectWinningCombinations(matrix, mockConfig);

    // Validate combinations detected for symbol "A"
    assertTrue(result.containsKey("A"));
    assertEquals(2, result.get("A").size());
    assertTrue(result.get("A").contains("same_symbols_horizontal"));
  }

  @Test
  void testDetectLinearSymbolCombination_ColumnWin() {
    // Matrix with a vertical column win
    String[][] matrix = {
      {"A", "B", "C"},
      {"A", "B", "C"},
      {"A", "B", "C"}
    };

    // Mock Config
    Config mockConfig = mock(Config.class);

    // Define winning combinations
    Map<String, WinningCombination> winCombinations = new HashMap<>();
    winCombinations.put("same_symbols_vertical", new WinningCombination(
      2.0,
      "linear_symbols",
      0,
      "group_vertical",
      List.of(
        List.of("0:0", "1:0", "2:0"), // First column
        List.of("0:1", "1:1", "2:1"), // Second column
        List.of("0:2", "1:2", "2:2")  // Third column
      )
    ));
    when(mockConfig.getWinCombinations()).thenReturn(winCombinations);

    // Detect winning combinations
    Map<String, List<String>> result = WinningCombinationDetector.detectWinningCombinations(matrix, mockConfig);

    // Validate combinations detected for symbols "A", "B", and "C"
    assertTrue(result.containsKey("A"));
    assertTrue(result.containsKey("B"));
    assertTrue(result.containsKey("C"));
    assertEquals(1, result.get("A").size());
    assertEquals(1, result.get("B").size());
    assertEquals(1, result.get("C").size());
    assertTrue(result.get("A").contains("same_symbols_vertical"));
    assertTrue(result.get("B").contains("same_symbols_vertical"));
    assertTrue(result.get("C").contains("same_symbols_vertical"));
  }

  @Test
  void testNoWinningCombinationDetected() {
    // Matrix without any winning combinations
    String[][] matrix = {
      {"A", "B", "C"},
      {"D", "E", "F"},
      {"G", "H", "I"}
    };

    // Mock Config
    Config mockConfig = mock(Config.class);

    // Define winning combinations
    Map<String, WinningCombination> winCombinations = new HashMap<>();
    winCombinations.put("same_symbol_3_times", new WinningCombination(
      2.0,
      "same_symbols",
      3,
      "group1",
      null
    ));
    when(mockConfig.getWinCombinations()).thenReturn(winCombinations);

    // Detect winning combinations
    Map<String, List<String>> result = WinningCombinationDetector.detectWinningCombinations(matrix, mockConfig);

    // Validate no combinations detected
    assertTrue(result.isEmpty());
  }

  @Test
  void testUnknownCombinationType() {
    // Matrix
    String[][] matrix = {
      {"A", "B", "C"},
      {"A", "E", "F"},
      {"A", "A", "I"}
    };

    // Mock Config
    Config mockConfig = mock(Config.class);

    // Define winning combinations with an invalid type
    Map<String, WinningCombination> winCombinations = new HashMap<>();
    winCombinations.put("unknown_combination", new WinningCombination(
      2.0,
      "unknown_type", // Invalid type
      0,
      "group_test",
      null
    ));
    when(mockConfig.getWinCombinations()).thenReturn(winCombinations);

    // Detect winning combinations
    Map<String, List<String>> result = WinningCombinationDetector.detectWinningCombinations(matrix, mockConfig);

    // Ensure nothing is detected (and a warning is logged)
    assertTrue(result.isEmpty());
  }
}