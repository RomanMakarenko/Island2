package life.herbivorous;

import annotations.Life;
import life.Organism;
import lombok.Data;

@Data
@Life
public class Horse extends Herbivorous {
    private final String ORGANISM_TYPE = "horse";

    public Horse() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Horse();
    }
}
