package am.devvibes.gameutils;

import am.devvibes.configs.Config;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
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
    List<String> appliedBonusSymbols,
    Config config) {
    double totalReward = 0;
    totalReward = calcRewardFromSymbolsAndWining(betAmount, appliedWinningCombinations, config, totalReward);
    checkIfLossThenOut(totalReward);
    totalReward = calcRewardFromBonuses(appliedBonusSymbols, totalReward);
    return totalReward;
  }

  public static List<String> determineBonusSymbols(String[][] matrix,
    Map<String, List<String>> appliedWinningCombinations) {
    List<String> bonusSymbols = new ArrayList<>();
    if (!appliedWinningCombinations.isEmpty()) {
      for (String[] row : matrix) {
        for (String cell : row) {
          if (cell.startsWith("+") || cell.endsWith("x")) {
            bonusSymbols.add(cell);
          }
        }
      }
    }
    return bonusSymbols;
  }

  private static double calcRewardFromBonuses(List<String> appliedBonusSymbols, double totalReward) {
    for (String bonus : appliedBonusSymbols) {
      if (bonus.endsWith("x")) {
        int multiplier = Integer.parseInt(bonus.replace("x", ""));
        totalReward *= multiplier;
      } else if (bonus.startsWith("+")) {
        int addValue = Integer.parseInt(bonus.replace("+", ""));
        totalReward += addValue;
      }
    }
    return totalReward;
  }

  private static double calcRewardFromSymbolsAndWining(double betAmount,
    Map<String, List<String>> appliedWinningCombinations,
    Config config, double totalReward) {
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
}