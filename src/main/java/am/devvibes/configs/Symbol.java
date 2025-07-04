package am.devvibes.configs;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Symbol {

  @JsonProperty("reward_multiplier")
  private Double rewardMultiplier;
  private String type;
  private Integer extra;
  private String impact;
}