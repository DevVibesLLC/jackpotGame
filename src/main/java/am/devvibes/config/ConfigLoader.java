package am.devvibes.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;

public class ConfigLoader {

  public static Config loadConfig(String configFilePath) throws Exception {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(new File(configFilePath), Config.class);
  }
}