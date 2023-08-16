package life.world;

import life.Animal;
import life.Organism;
import life.plants.Grass;

import java.util.*;

public class World {
    private final int WIDTH = 100;
    private final int HEIGHT = 20;
    private HashMap<Point, ArrayList<Organism>> islandMap;

    public World() {
        Island island = new Island();
        islandMap = island.generateLifeOnIsland(WIDTH, HEIGHT);
    }

    public void makeIteration() {
        for (Map.Entry<Point, ArrayList<Organism>> entry : islandMap.entrySet()) {
            Point point = entry.getKey();
            ArrayList<Organism> organisms = entry.getValue();
            Collections.shuffle(organisms);
            for (Organism organism : organisms) {
                Animal animal = getAnimalType(organism);
                if (animal != null && animal.isAlive()) {
                    organisms = eat(animal, organisms);
                }
            }
            System.out.println();
        }

        pair();
        move();
    }

    private Animal getAnimalType(Organism organism) {
        if (organism instanceof Animal) {
            return (Animal) organism;
        }
        return null;
    }

    private ArrayList<Organism> eat(Animal animal, ArrayList<Organism> organismsOnPoint) {
        List<String> potentialFoodListInIteration = animal.getPotentialIterationFoodList();
        potentialFoodListInIteration.stream().forEach(food -> {
            for (Organism organism : organismsOnPoint) {
                if (organism.getORGANISM_TYPE().equals(food) && organism.isAlive()) {
                    organism.setAlive(false);
                    break;
                }
            }
        });
        return organismsOnPoint;
    }

    private void pair() {

    }

    private void move() {

    }

}
