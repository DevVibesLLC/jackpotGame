package am.devvibes.configs;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Config {

  private int columns;
  private int rows;
  private Map<String, Symbol> symbols;
  private Probabilities probabilities;
  @JsonProperty("win_combinations")
  private Map<String, WinningCombination> winCombinations;
}