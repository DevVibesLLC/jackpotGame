package am.devvibes.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Data;

@Data
public class Probabilities {

  @JsonProperty("standard_symbols")
  private List<SymbolProbability> standardSymbols;
  @JsonProperty("bonus_symbols")
  private BonusSymbolProbabilities bonusSymbols;
}