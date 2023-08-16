package life.herbivorous;

import annotations.Life;
import life.Organism;
import lombok.Data;

@Data
@Life
public class Caterpillar extends Herbivorous {
    private final String ORGANISM_TYPE = "caterpillar";

    public Caterpillar() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Caterpillar();
    }
}
