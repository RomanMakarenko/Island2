package life.herbivorous;

import annotations.Life;
import life.Organism;
import lombok.Data;

@Data
@Life
public class Duck extends Herbivorous {
    private final String ORGANISM_TYPE = "duck";

    public Duck() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Duck();
    }
}
