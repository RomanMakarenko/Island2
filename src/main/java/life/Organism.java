package life;

import life.behavior.Dying;
import lombok.Data;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Data
public abstract class Organism implements Dying {
    protected final String ORGANISM_TYPE = "Organism";
    protected boolean isAlive;
    protected double weight;
    protected int maxPopulationSize;
    protected HashMap<String, Integer> chanceForLoot = new HashMap<>();

    public abstract Organism createClone();

    public List<String> getPotentialIterationFoodList() {
        Set<Map.Entry<String, Integer>> entries = chanceForLoot.entrySet();
        List<String> potentialIterationFoodList = new ArrayList<>();
        for (Map.Entry<String, Integer> pair : entries) {
            if (ThreadLocalRandom.current().nextInt(0, 100) < pair.getValue()) {
                potentialIterationFoodList.add(pair.getKey());
            }
        }
        return potentialIterationFoodList;
    }
}
