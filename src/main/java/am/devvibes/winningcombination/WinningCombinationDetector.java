package am.devvibes.winningcombination;

import am.devvibes.configs.Config;
import am.devvibes.configs.WinningCombination;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class WinningCombinationDetector {

  private static final Logger log = LoggerFactory.getLogger(WinningCombinationDetector.class);

  public static Map<String, List<String>> detectWinningCombinations(String[][] matrix, Config config) {
    Map<String, List<String>> appliedWinningCombinations = new HashMap<>();

    for (String comboName : config.getWinCombinations().keySet()) {
      WinningCombination winningCombination = config.getWinCombinations().get(comboName);

      switch (winningCombination.getWhen()) {
        case "same_symbols":
          detectSameSymbolCombination(matrix, winningCombination, comboName, appliedWinningCombinations);
          break;
        case "linear_symbols":
          detectLinearSymbolCombination(matrix, winningCombination, comboName, appliedWinningCombinations);
          break;
        default:
          log.warn("Unknown winning combination type: {}", winningCombination.getWhen());
      }
    }
    return appliedWinningCombinations;
  }

  private static void detectSameSymbolCombination(
    String[][] matrix,
    WinningCombination combination,
    String comboName,
    Map<String, List<String>> appliedWinningCombinations
  ) {
    Map<String, Integer> symbolCounts = new HashMap<>();
    for (String[] row : matrix) {
      for (String cell : row) {
        symbolCounts.put(cell, symbolCounts.getOrDefault(cell, 0) + 1);
      }
    }
    for (Map.Entry<String, Integer> entry : symbolCounts.entrySet()) {
      String symbol = entry.getKey();
      int count = entry.getValue();

      if (count >= combination.getCount()) {
        appliedWinningCombinations
          .computeIfAbsent(symbol, k -> new ArrayList<>())
          .add(comboName);
      }
    }
  }

  private static void detectLinearSymbolCombination(
    String[][] matrix,
    WinningCombination combination,
    String comboName,
    Map<String, List<String>> appliedWinningCombinations
  ) {
    List<List<String>> coveredAreas = combination.getCoveredAreas();

    for (List<String> area : coveredAreas) {
      Set<String> symbolsInArea = new HashSet<>();

      for (String coordinate : area) {
        String[] parts = coordinate.split(":");
        int row = Integer.parseInt(parts[0]);
        int col = Integer.parseInt(parts[1]);
        symbolsInArea.add(matrix[row][col]);
      }

      if (symbolsInArea.size() == 1) {
        String symbol = symbolsInArea.iterator().next(); // Get the single symbol
        appliedWinningCombinations
          .computeIfAbsent(symbol, k -> new ArrayList<>())
          .add(comboName);
      }
    }
  }
}