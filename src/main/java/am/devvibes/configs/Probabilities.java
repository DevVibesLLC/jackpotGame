package am.devvibes.configs;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Probabilities {

  @JsonProperty("standard_symbols")
  private List<SymbolProbability> standardSymbols;
  @JsonProperty("bonus_symbols")
  private BonusSymbolProbabilities bonusSymbols;
}