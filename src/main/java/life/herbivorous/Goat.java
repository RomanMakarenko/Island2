package life.herbivorous;

import annotations.Life;
import life.Organism;
import lombok.Data;

@Data
@Life
public class Goat extends Herbivorous {
    private final String ORGANISM_TYPE = "goat";

    public Goat() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Goat();
    }
}
