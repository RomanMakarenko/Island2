package life;

import com.fasterxml.jackson.databind.JsonNode;
import dataManager.ConfigurationManager;
import life.behavior.Eating;
import life.behavior.Moving;
import life.behavior.Pairing;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public abstract class Animal extends Organism implements Eating, Moving, Pairing {
    protected int maxSpeed;
    protected double mealSize;
    protected double eatenSize;
    protected boolean isPaired;
    protected int chanceForPairing;

    protected void loadConfig(String animalType) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        JsonNode classNode = configurationManager.getConfigNode(animalType);
        if (classNode != null) {
            isAlive = true;
            weight = classNode.get("weight").asDouble();
            maxPopulationSize = classNode.get("maxPopulationSize").asInt();
            maxSpeed = classNode.get("maxSpeed").asInt();
            mealSize = classNode.get("mealSize").asDouble();
            chanceForPairing = classNode.get("chanceForPairing").asInt();
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

    @Override
    public ArrayList<Organism> eat(ArrayList<Organism> organismsOnPoint) {
        Collections.shuffle(organismsOnPoint);
        List<String> potentialFoodListInIteration = this.getPotentialIterationFoodList();
        potentialFoodListInIteration.stream().forEach(food -> {
            for (Organism organism : organismsOnPoint) {
                if (organism.getORGANISM_TYPE().equals(food) && organism.isAlive()) {
                    organism.setAlive(false);
                    this.setEatenSize(organism.getWeight());
                    break;
                }
            }
        });
        return organismsOnPoint;
    }

    @Override
    public ArrayList<Animal> pair(ArrayList<Animal> animalsOnPoint) {
        if (this.isPaired() || !this.wantToPairing()) {
            return new ArrayList<>();
        }
        Collections.shuffle(animalsOnPoint);
        ArrayList<Animal> newCreatedAnimals = new ArrayList<>();
        animalsOnPoint.forEach(comparedAnimal -> {
            if (
                    comparedAnimal != this
                    && !comparedAnimal.isPaired()
                    && !this.isPaired()
                    && comparedAnimal.getORGANISM_TYPE().equals(this.getORGANISM_TYPE())
            ) {
                this.setPaired(true);
                comparedAnimal.setPaired(true);
                newCreatedAnimals.add((Animal) this.createClone());
            }
        });
        return newCreatedAnimals;
    }

    private boolean wantToPairing() {
        return ThreadLocalRandom.current().nextInt(
                0,
                100
        ) <= this.getChanceForPairing();
    }
}
