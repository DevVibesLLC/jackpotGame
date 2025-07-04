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
public class WinningCombination {

  @JsonProperty("reward_multiplier")
  private Double rewardMultiplier;
  private String when;
  private Integer count;
  private String group;
  @JsonProperty("covered_areas")
  private List<List<String>> coveredAreas;
}