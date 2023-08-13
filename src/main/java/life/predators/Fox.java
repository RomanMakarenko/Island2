package life.predators;

import annotations.Life;
import life.Organism;
import lombok.Getter;

@Getter
@Life
public class Fox extends Predator {
    private final String ORGANISM_TYPE = "fox";

    public Fox() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Fox();
    }
}
