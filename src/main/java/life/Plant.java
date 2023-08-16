package life;

import com.fasterxml.jackson.databind.JsonNode;
import dataManager.ConfigurationManager;
import life.behavior.Growth;
import life.world.Point;

public abstract class Plant extends Organism implements Growth {
    protected void loadConfig(String animalType) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        JsonNode classNode = configurationManager.getConfigNode(animalType);
        if (classNode != null) {
            isAlive = true;
            weight = classNode.get("weight").asDouble();
            maxPopulationSize = classNode.get("maxPopulationSize").asInt();
        }
    }

    @Override
    public void growth(Point point) {
        this.setXAfterMove(point.getX());
        this.setYAfterMove(point.getY());
    }
}
