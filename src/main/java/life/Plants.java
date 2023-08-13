package life;

import com.fasterxml.jackson.databind.JsonNode;
import dataManager.ConfigurationManager;

public abstract class Plants extends Organism {
    protected void loadConfig(String animalType) {
        JsonNode classNode = ConfigurationManager.getConfigNode(animalType);
        if (classNode != null) {
            weight = classNode.get("weight").asDouble();
            maxPopulationSize = classNode.get("maxPopulationSize").asInt();
        }
    }
}
