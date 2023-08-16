package life.herbivorous;

import annotations.Life;
import life.Organism;
import lombok.Data;

@Data
@Life
public class Mouse extends Herbivorous {
    private final String ORGANISM_TYPE = "mouse";

    public Mouse() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Mouse();
    }
}
