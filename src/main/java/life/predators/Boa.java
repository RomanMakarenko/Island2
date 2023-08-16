package life.predators;

import annotations.Life;
import life.Organism;
import lombok.Data;

@Data
@Life
public class Boa extends Predator {
    private final String ORGANISM_TYPE = "boa";

    public Boa() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Boa();
    }
}
