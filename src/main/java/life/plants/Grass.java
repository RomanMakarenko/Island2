package life.plants;

import annotations.Life;
import life.Organism;
import life.Plants;
import lombok.Data;

@Life
@Data
public class Grass extends Plants {
    private final String ORGANISM_TYPE = "grass";

    public Grass() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Grass();
    }
}
