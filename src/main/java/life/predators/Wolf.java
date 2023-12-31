package life.predators;

import annotations.Life;
import life.Organism;
import lombok.Data;

@Life
@Data
public class Wolf extends Predator {
    private final String ORGANISM_TYPE = "wolf";
    public Wolf() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Wolf();
    }
}
