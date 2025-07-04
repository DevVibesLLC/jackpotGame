package am.devvibes.matrix;

import static am.devvibes.gameutils.GameUtils.objectMapper;

import am.devvibes.configs.BonusSymbolProbabilities;
import am.devvibes.configs.Config;
import am.devvibes.configs.SymbolProbability;
import com.fasterxml.jackson.databind.SerializationFeature;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class MatrixGenerator {

  private static final Logger log = LoggerFactory.getLogger(MatrixGenerator.class);

  public static String[][] generateMatrix(Config config) {
    int rows = config.getRows();
    int columns = config.getColumns();
    String[][] matrix = new String[rows][columns];
    List<SymbolProbability> standardSymbolProbabilities = config.getProbabilities().getStandardSymbols();

    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        int finalRow = row;
        int finalColumn = column;
        SymbolProbability probabilities = standardSymbolProbabilities.stream()
          .filter(p -> p.getRow() == finalRow && p.getColumn() == finalColumn)
          .findFirst()
          .orElse(standardSymbolProbabilities.getFirst());
        matrix[row][column] = generateSymbol(probabilities.getSymbols());
      }
    }
    applyBonusSymbols(matrix, config.getProbabilities().getBonusSymbols());
    return matrix;
  }

  public static void printGameOverview(double reward, Map<String, List<String>> appliedWinningCombinations,
    String appliedBonusSymbol, double betAmount) {
    try {
      objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
      Map<String, Object> output = Map.of(
        "beted amount", betAmount,
        "reward", reward,
        "applied_winning_combinations", appliedWinningCombinations,
        "applied_bonus_symbol", appliedBonusSymbol
      );
      String gameOverview = objectMapper.writeValueAsString(output);
      log.info("Game overview: {}", gameOverview);

    } catch (Exception e) {
      log.error("Failed to serialize the result into JSON: {}", e.getMessage());

    }
  }

  public static void printMatrixSeparately(String[][] matrix) {
    log.info("Matrix:");
    for (String[] row : matrix) {
      String matrixLine = "[ " + String.join(", ", row) + " ]";
      log.info(matrixLine);
    }
  }

  private static String generateSymbol(Map<String, Integer> probabilities) {
    List<String> weightedSymbols = probabilities.entrySet()
      .stream()
      .flatMap(entry -> Collections.nCopies(entry.getValue(), entry.getKey()).stream())
      .toList();
    return weightedSymbols.get(am.devvibes.gameutils.GameUtils.random.nextInt(weightedSymbols.size()));
  }

  private static void applyBonusSymbols(String[][] matrix, BonusSymbolProbabilities bonusProbabilities) {
    Map<String, Integer> probabilities = bonusProbabilities.getSymbols();

    List<String> weightedBonusSymbols = probabilities.entrySet()
      .stream()
      .flatMap(entry -> Collections.nCopies(entry.getValue(), entry.getKey()).stream())
      .toList();

    for (int i = 0; i < matrix.length; i++) {
      for (int j = 0; j < matrix[i].length; j++) {
        if (am.devvibes.gameutils.GameUtils.random.nextDouble() < 0.1) { // Example: 10% chance for bonus symbol
          matrix[i][j] = weightedBonusSymbols.get(
            am.devvibes.gameutils.GameUtils.random.nextInt(weightedBonusSymbols.size()));
        }
      }
    }
  }
}