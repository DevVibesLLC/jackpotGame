package am.devvibes.configs;

import static am.devvibes.gameutils.GameUtils.objectMapper;

import java.io.File;
import java.io.IOException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class ConfigLoader {

  public static Config loadConfig(String configFilePath) {
    try {
      return objectMapper.readValue(new File(configFilePath), Config.class);
    } catch (IOException e) {
      throw new IllegalStateException("Failed to load configuration file.", e);
    }
  }
}