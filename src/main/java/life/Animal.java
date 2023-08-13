package life;

import com.fasterxml.jackson.databind.JsonNode;
import dataManager.ConfigurationManager;
import life.behavior.Eating;
import life.behavior.Moving;
import life.behavior.Pairing;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public abstract class Animal extends Organism implements Eating, Moving, Pairing {
    protected int maxSpeed;
    protected double mealSize;

    protected void loadConfig(String animalType) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        JsonNode classNode = configurationManager.getConfigNode(animalType);
        if (classNode != null) {
            isAlive = true;
            weight = classNode.get("weight").asDouble();
            maxPopulationSize = classNode.get("maxPopulationSize").asInt();
            maxSpeed = classNode.get("maxSpeed").asInt();
            mealSize = classNode.get("mealSize").asDouble();
            JsonNode chanceForLootNode = classNode.get("chanceForLoot");
            if (chanceForLootNode != null) {
                chanceForLootNode.fields().forEachRemaining(entry -> {
                    String animal = entry.getKey();
                    int chance = entry.getValue().asInt();
                    chanceForLoot.put(animal, chance);
                });
            } else {
                System.out.println("Missed chanceForLoot block");
            }
        }
    }
}
