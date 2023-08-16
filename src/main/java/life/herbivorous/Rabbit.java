package life.herbivorous;

import annotations.Life;
import life.Organism;
import lombok.Data;

@Data
@Life
public class Rabbit extends Herbivorous {
    private final String ORGANISM_TYPE = "rabbit";

    public Rabbit() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Rabbit();
    }
}
