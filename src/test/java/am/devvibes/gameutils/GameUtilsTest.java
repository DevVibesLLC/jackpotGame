package am.devvibes.gameutils;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import am.devvibes.configs.Config;
import am.devvibes.configs.Symbol;
import am.devvibes.configs.WinningCombination;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;

class GameUtilsTest {

  // Mocked configuration
  Config mockConfig = mock(Config.class);

  @Test
  void testCheckIfLoss_WinScenario() {
    // Win scenario where reward > 0
    assertDoesNotThrow(() -> GameUtils.checkIfLoss(100));
  }

  @Test
  void testDetermineBonusSymbols_WithBonuses() {
    String[][] matrix = {
      {"D", "B", "10x"},
      {"E", "F", "C"},
      {"+500", "F", "E"}
    };

    Map<String, List<String>> appliedWinningCombinations = Map.of(
      "D", List.of("same_symbol_5_times"),
      "F", List.of("same_symbol_3_times")
    );

    // Extract bonus symbols
    List<String> result = GameUtils.determineBonusSymbols(matrix, appliedWinningCombinations);

    // Validate extracted symbols
    assertEquals(2, result.size());
    assertTrue(result.contains("10x"));
    assertTrue(result.contains("+500"));
  }

  @Test
  void testDetermineBonusSymbols_NoWinningCombinations() {
    String[][] matrix = {
      {"D", "B", "10x"},
      {"E", "F", "C"},
      {"+500", "F", "E"}
    };

    Map<String, List<String>> appliedWinningCombinations = new HashMap<>();  // No winning combinations

    // Extract bonus symbols
    List<String> result = GameUtils.determineBonusSymbols(matrix, appliedWinningCombinations);

    // No bonus symbols since there are no winning combinations
    assertTrue(result.isEmpty());
  }

  @Test
  void testCalculateReward_WithBonuses() {
    // Mock Config
    Map<String, Symbol> symbols = new HashMap<>();
    symbols.put("A", new Symbol(5.0, "standard", null, null)); // Reward multiplier 5
    symbols.put("B", new Symbol(3.0, "standard", null, null));

    when(mockConfig.getSymbols()).thenReturn(symbols);

    // Winning combinations
    Map<String, WinningCombination> winCombinations = new HashMap<>();
    winCombinations.put("same_symbol_5_times", new WinningCombination(5.0, "same_symbols", 5, "group1", null));

    when(mockConfig.getWinCombinations()).thenReturn(winCombinations);

    // Applied winning combinations
    Map<String, List<String>> appliedWinningCombinations = Map.of(
      "A", List.of("same_symbol_5_times")
    );

    // Bonus symbols
    List<String> bonusSymbols = List.of("+500", "10x");

    // Run reward calculation
    double reward = GameUtils.calculateReward(100, appliedWinningCombinations, bonusSymbols, mockConfig);

    // Reward calculation
    // Base reward for "A" => 100 * 5 (symbol multiplier) * 5 (winning combination multiplier) = 2500
    // Apply bonus symbols:
    //   +500 => 2500 + 500 = 3000
    //   10x => 3000 * 10 = 30000
    assertEquals(30000, reward);
  }

  @Test
  void testCalculateReward_NoWinningCombinations() {
    // Mock Config
    when(mockConfig.getSymbols()).thenReturn(new HashMap<>());
    when(mockConfig.getWinCombinations()).thenReturn(new HashMap<>());

    // No winning combinations or bonuses
    Map<String, List<String>> appliedWinningCombinations = new HashMap<>();
    List<String> bonusSymbols = new ArrayList<>();

    // Run reward calculation
    double reward = GameUtils.calculateReward(100, appliedWinningCombinations, bonusSymbols, mockConfig);

    // Since there are no winning combinations, reward = 0
    assertEquals(0, reward);
  }
}