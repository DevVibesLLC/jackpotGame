package am.devvibes.configs;

import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SymbolProbability {

  private int column;
  private int row;
  private Map<String, Integer> symbols;
}