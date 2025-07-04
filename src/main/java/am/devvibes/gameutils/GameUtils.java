package am.devvibes.gameutils;

import am.devvibes.configs.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import lombok.experimental.UtilityClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@UtilityClass
public class GameUtils {

  public static final Random random = new Random();
  public static final ObjectMapper objectMapper = new ObjectMapper();

  private static final Logger log = LoggerFactory.getLogger(GameUtils.class);

  public static void checkIfLossThenOut(double reward) {
    if (reward == 0) {
      log.error("You Lose your Bet: {}", reward);
      System.exit(1);
    }
  }

  public static double calculateReward(double betAmount, Map<String, List<String>> appliedWinningCombinations,
    Config config) {
    double totalReward = 0;

    for (Entry<String, List<String>> symbol : appliedWinningCombinations.entrySet()) {
      String key = symbol.getKey();
      double symbolRewardMultiplier = config.getSymbols().get(key).getRewardMultiplier();
      double symbolBaseReward = betAmount * symbolRewardMultiplier;

      for (String combination : appliedWinningCombinations.get(key)) {
        double combinationMultiplier = config.getWinCombinations().get(combination).getRewardMultiplier();
        symbolBaseReward *= combinationMultiplier;
      }
      totalReward += symbolBaseReward;
    }
    return totalReward;
  }

  public static String determineBonusSymbol(String[][] matrix, Map<String, List<String>> appliedWinningCombinations) {
    if (!appliedWinningCombinations.isEmpty()) {
      for (String[] row : matrix) {
        for (String cell : row) {
          if (cell.startsWith("+") || cell.endsWith("x")) {
            return cell;
          }
        }
      }
    }
    return "";
  }
}