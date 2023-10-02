package life.world;

import dataManager.DataCollector;
import life.Animal;
import life.Organism;
import life.Plant;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class Island {
    private int worldWidth;
    private int worldHeight;
    public static volatile Island instance;
    public static volatile HashMap<Point, ArrayList<Organism>> lifeOnIsland;
    private final int MIN_ORGANISM_POPULATION = 0;

    private Island(int worldWidth, int worldHeight) {
        this.worldWidth = worldWidth;
        this.worldHeight = worldHeight;
    }

    public static Island getInstance(int worldWidth, int worldHeight) {
        Island result = instance;
        if (result != null) {
            return result;
        }
        synchronized (Island.class) {
            if (instance == null) {
                instance = new Island(worldWidth, worldHeight);
                lifeOnIsland = instance.generateLifeOnIsland();
            }
            return instance;
        }
    }

    public HashMap<Point, ArrayList<Organism>> getLifeOnIsland() {
        return lifeOnIsland;
    }

    public ArrayList<Organism> getOrganismListOnPoint(Point point) {
        ArrayList<Organism> lifeOnPoint = lifeOnIsland.get(point);
        if (lifeOnPoint == null) {
            return new ArrayList<>();
        }
        return lifeOnIsland.get(point);
    }

    public ArrayList<Animal> getAnimalsOnPoint(Point point) {
        if (lifeOnIsland.get(point) == null) {
            return new ArrayList<>();
        }
        return lifeOnIsland.get(point).stream()
                .filter(currentOrganism -> currentOrganism instanceof Animal)
                .map(currentOrganism -> (Animal) currentOrganism)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public ArrayList<Plant> getPlantsOnPoint(Point point) {
        if (lifeOnIsland.get(point) == null) {
            return new ArrayList<>();
        }
        return lifeOnIsland.get(point).stream()
                .filter(currentOrganism -> currentOrganism instanceof Plant)
                .map(currentOrganism -> (Plant) currentOrganism)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public void addOrganismsOnPoint(ArrayList<? extends Organism> organisms, Point point) {
        lifeOnIsland.get(point).addAll(organisms);
    }

    private HashMap<Point, ArrayList<Organism>> generateLifeOnIsland() {
        HashMap<Point, ArrayList<Organism>> lifeOnIsland = new HashMap<>();
        DataCollector dataCollector = new DataCollector();
        List<Organism> organismList = dataCollector.getOrganismList();
        for (int i = 0; i < worldWidth; i++) {
            for (int j = 0; j < worldHeight; j++) {
                Point currentPoint = new Point(i, j);
                ArrayList<Organism> organismsAtPoint = new ArrayList<>();
                for (Organism organism : organismList) {
                    int organismPopulationOnPoint = ThreadLocalRandom.current().nextInt(
                            MIN_ORGANISM_POPULATION,
                            organism.getMaxPopulationSize()
                    );
                    for (int k = 0; k < organismPopulationOnPoint; k++) {
                        Organism newOrganism = organism.createClone();
                        newOrganism.setX(i);
                        newOrganism.setY(j);
                        newOrganism.setXAfterMove(i);
                        newOrganism.setYAfterMove(j);
                        organismsAtPoint.add(newOrganism);
                    }
                }
                lifeOnIsland.put(currentPoint, organismsAtPoint);
            }
        }
        return lifeOnIsland;
    }

    public void refreshMap() {
        HashMap<Point, ArrayList<Organism>> newIslandMap = new HashMap<>();
        for (Map.Entry<Point, ArrayList<Organism>> entry : lifeOnIsland.entrySet()) {
            ArrayList<Organism> organisms = entry.getValue();
            for (Organism organism : organisms) {
                if (!organism.isAlive()) {
                    continue;
                }
                if (organism instanceof Animal) {
                    ((Animal) organism).setPaired(false);
                }
                Point newKey = new Point(organism.getXAfterMove(), organism.getYAfterMove());
                if (newIslandMap.containsKey(newKey)) {
                    organism.setX(organism.getXAfterMove());
                    organism.setY(organism.getYAfterMove());
                    newIslandMap.get(newKey).add(organism);
                } else {
                    ArrayList<Organism> newOrganismList = new ArrayList<>();
                    newOrganismList.add(organism);
                    newIslandMap.put(newKey, newOrganismList);
                }
            }
        }
        lifeOnIsland = newIslandMap;
    }

    public void cleanDeadOrganism(Point point) {
        Iterator<Organism> iterator = lifeOnIsland.get(point).iterator();
        while (iterator.hasNext()) {
            Organism organism = iterator.next();
            if (!organism.isAlive()) {
                iterator.remove();
            }
        }
    }
}






