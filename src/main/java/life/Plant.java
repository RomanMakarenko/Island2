package life;

import com.fasterxml.jackson.databind.JsonNode;
import dataManager.ConfigurationManager;
import life.behavior.Growth;
import life.world.Point;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

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
    public ArrayList<Plant> growth(Point point, ArrayList<Plant> plantsOnPoint) {
        this.setXAfterMove(point.getX());
        this.setYAfterMove(point.getY());
        ArrayList<Plant> newCreatedPlants = new ArrayList<>();
        if (plantsOnPoint.size() < this.maxPopulationSize) {
            int numberOfNewPlantsOnPoint = ThreadLocalRandom.current().nextInt(
                    plantsOnPoint.size(),
                    this.maxPopulationSize
            );
            for (int i = 0; i < numberOfNewPlantsOnPoint; i++) {
                newCreatedPlants.add((Plant) this.createClone());
            }
        }
        return newCreatedPlants;
    }
}
