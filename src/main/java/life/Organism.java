package life;

import life.behavior.Dying;
import lombok.Data;

@Data
public abstract class Organism implements Dying {
    protected double weight;
    protected int maxPopulationSize;

    public abstract Organism createClone();
}
