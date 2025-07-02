package am.devvibes.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Data;


@Data
public class Config {

  private int columns;
  private int rows;
  private Map<String, Symbol> symbols;
  private Probabilities probabilities;
  @JsonProperty("win_combinations")
  private Map<String, WinningCombination> winCombinations;
}