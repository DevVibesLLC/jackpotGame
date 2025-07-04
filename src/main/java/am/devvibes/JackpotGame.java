package am.devvibes;

import static am.devvibes.arguments.InputArgumentsProvider.getInputArguments;
import static am.devvibes.gameutils.GameUtils.calculateReward;
import static am.devvibes.gameutils.GameUtils.determineBonusSymbols;
import static am.devvibes.matrix.MatrixGenerator.printGameOverview;
import static am.devvibes.matrix.MatrixGenerator.printMatrixSeparately;
import static am.devvibes.validations.ValidationUtils.validateConfig;

import am.devvibes.arguments.InputArgument;
import am.devvibes.configs.Config;
import am.devvibes.configs.ConfigLoader;
import am.devvibes.matrix.MatrixGenerator;
import am.devvibes.winningcombination.WinningCombinationDetector;
import java.util.List;
import java.util.Map;

public class JackpotGame {
  public static void main(String[] args) {
    InputArgument result = getInputArguments(args);
    double betAmount = result.getBetAmount();
    Config config = ConfigLoader.loadConfig(result.getConfigFilePath());
    validateConfig(config);
    String[][] matrix = MatrixGenerator.generateMatrix(config);
    printMatrixSeparately(matrix);
    Map<String, List<String>> appliedWinningCombinations =
      WinningCombinationDetector.detectWinningCombinations(matrix, config);
    List<String> appliedBonusSymbols = determineBonusSymbols(matrix, appliedWinningCombinations);
    double reward = calculateReward(betAmount, appliedWinningCombinations, appliedBonusSymbols, config);
    printGameOverview(reward, appliedWinningCombinations, appliedBonusSymbols, betAmount);
  }
}
