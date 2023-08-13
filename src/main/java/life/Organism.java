package life;

import life.behavior.Dying;
import lombok.Data;

import java.util.HashMap;

@Data
public abstract class Organism implements Dying {
    protected boolean isAlive;
    protected double weight;
    protected int maxPopulationSize;
    protected HashMap<String, Integer> chanceForLoot = new HashMap<>();

    public abstract Organism createClone();
}
