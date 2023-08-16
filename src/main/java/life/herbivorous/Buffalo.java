package life.herbivorous;
import annotations.Life;
import life.Organism;
import lombok.Data;

@Data
@Life
public class Buffalo extends Herbivorous {
    private final String ORGANISM_TYPE = "buffalo";

    public Buffalo() {
        loadConfig(ORGANISM_TYPE);
    }

    @Override
    public Organism createClone() {
        return new Buffalo();
    }
}
