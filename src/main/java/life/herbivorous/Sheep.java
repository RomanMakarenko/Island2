package life.herbivorous;

import annotations.Life;
import life.Organism;
import lombok.Data;

@Data
@Life
public class Sheep extends Herbivorous {
    private final String ORGANISM_TYPE = "sheep";

    public Sheep() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Sheep();
    }
}
