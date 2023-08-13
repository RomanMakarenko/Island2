package life;

import com.fasterxml.jackson.databind.JsonNode;
import dataManager.ConfigurationManager;

public abstract class Plants extends Organism {
    protected void loadConfig(String animalType) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        JsonNode classNode = configurationManager.getConfigNode(animalType);
        if (classNode != null) {
            isAlive = true;
            weight = classNode.get("weight").asDouble();
            maxPopulationSize = classNode.get("maxPopulationSize").asInt();
        }
    }
}
