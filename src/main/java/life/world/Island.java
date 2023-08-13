package life.world;

import dataManager.DataCollector;
import life.Organism;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Island {
    private final int WIDTH = 100;
    private final int HEIGHT = 20;
    private final int MIN_ORGANISM_POPULATION = 0;

    public HashMap<Point, ArrayList<Organism>> generateLifeOnIsland() {
        HashMap<Point, ArrayList<Organism>> lifeOnIsland = new HashMap<>();
        DataCollector dataCollector = new DataCollector();
        List<Organism> organismList = dataCollector.getOrganismList();
        for (int i = 0; i < this.WIDTH; i++) {
            for (int j = 0; j < this.HEIGHT; j++) {
                Point currentPoint = new Point(i, j);
                ArrayList<Organism> organismsAtPoint = new ArrayList<>();
                for (Organism organism: organismList) {
                   int organismPopulationOnPoint = ThreadLocalRandom.current().nextInt(
                           MIN_ORGANISM_POPULATION,
                           organism.getMaxPopulationSize()
                   );
                    for (int k = 0; k < organismPopulationOnPoint; k++) {
                        organismsAtPoint.add(organism.createClone());
                    }
                }
                lifeOnIsland.put(currentPoint, organismsAtPoint);
            }
        }
        return lifeOnIsland;
    }
}






