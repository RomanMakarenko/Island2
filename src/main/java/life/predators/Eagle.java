package life.predators;

import annotations.Life;
import life.Organism;
import lombok.Data;

@Data
@Life
public class Eagle extends Predator {
    private final String ORGANISM_TYPE = "eagle";

    public Eagle() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Eagle();
    }
}
