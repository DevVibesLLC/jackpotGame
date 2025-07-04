package am.devvibes.configs;

import java.util.Map;
import lombok.Data;

@Data
public class SymbolProbability {

  private int column;
  private int row;
  private Map<String, Integer> symbols;
}