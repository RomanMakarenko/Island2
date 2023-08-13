package life.herbivorous;

import annotations.Life;
import life.Organism;
import lombok.Data;

@Data
@Life
public class Deer extends Herbivorous {
    private final String ORGANISM_TYPE = "deer";

    public Deer() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Deer();
    }
}
