package life.herbivorous;

import annotations.Life;
import life.Organism;
import lombok.Data;

@Data
@Life
public class Hog extends Herbivorous {
    private final String ORGANISM_TYPE = "hog";

    public Hog() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Hog();
    }
}
