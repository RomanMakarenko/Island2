package dataManager;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ConfigurationManager {
    private static final String CONFIG_DIRECTORY = "src/configs/organisms/";
    private static ObjectMapper objectMapper = new ObjectMapper();

    private static Map<String, JsonNode> configCache = new HashMap<>();

    public JsonNode getConfigNode(String organismType) {
        if (configCache.containsKey(organismType)) {
            return configCache.get(organismType);
        } else {
            try {
                File configFile = new File(CONFIG_DIRECTORY + organismType + ".json");
                if (configFile.exists()) {
                    JsonNode configNode = objectMapper.readTree(configFile);
                    configCache.put(organismType, configNode);
                    return configNode;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
