package life.predators;

import annotations.Life;
import life.Organism;
import lombok.Data;

@Data
@Life
public class Bear extends Predator {
    private final String ORGANISM_TYPE = "bear";

    public Bear() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Bear();
    }
}
