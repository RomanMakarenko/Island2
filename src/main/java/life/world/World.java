package life.world;

import life.Animal;
import life.Organism;
import life.Plant;

import java.util.*;
import java.util.stream.Collectors;

public class World {
    public static final int WIDTH = 100;
    public static final int HEIGHT = 20;
    private HashMap<Point, ArrayList<Organism>> islandMap;

    public World() {
        Island island = new Island();
        islandMap = island.generateLifeOnIsland(WIDTH, HEIGHT);
    }

    public void makeIteration() {
        int animalsCountOnIteration = 0;
        int organismsCountOnIteration = 0;
        int numberOfEatenOrganismsOnIteration = 0;
        int numberOfBornAnimalsOnIteration = 0;
        for (Map.Entry<Point, ArrayList<Organism>> entry : islandMap.entrySet()) {
            Point point = entry.getKey();
            ArrayList<Organism> organisms = entry.getValue();
            organismsCountOnIteration += organisms.size();
            Collections.shuffle(organisms);
            ArrayList<Animal> animals = getAnimalsOnPoint(organisms);
            animalsCountOnIteration += animals.size();
            int organismsCountBeforeEat = organisms.size();
            eatAction(organisms, animals);
            numberOfEatenOrganismsOnIteration += organismsCountBeforeEat - organisms.size();
            animals = getAnimalsOnPoint(organisms);
            pairAction(organisms, animals);
            animals = getAnimalsOnPoint(organisms);
            movingAction(point, animals);
            ArrayList<Plant> plants = getPlantsOnPoint(organisms);
            growthAction(point, plants);
        }
        System.out.println("Organisms count on iteration: " + organismsCountOnIteration);
        System.out.println("Animals count on iteration: " + animalsCountOnIteration);
        System.out.println("Number of eaten organisms on iteration: " + numberOfEatenOrganismsOnIteration);
        refreshMap();
    }

    private void refreshMap() {
        HashMap<Point, ArrayList<Organism>> newIslandMap = new HashMap<>();
        for (Map.Entry<Point, ArrayList<Organism>> entry : islandMap.entrySet()) {
            ArrayList<Organism> organisms = entry.getValue();
            for (Organism organism : organisms) {
                Point newKey = new Point(organism.getXAfterMove(), organism.getYAfterMove());
                if (newIslandMap.containsKey(newKey)) {
                    newIslandMap.get(newKey).add(organism);
                } else {
                    ArrayList<Organism> newOrganismList = new ArrayList<>();
                    newOrganismList.add(organism);
                    newIslandMap.put(newKey, newOrganismList);
                }
            }
        }
        islandMap = newIslandMap;
    }

    private void growthAction(Point point, ArrayList<Plant> plants) {
        plants.forEach(plant -> {
            plant.growth(point);
        });
    }

    private void movingAction(Point point, ArrayList<Animal> animals) {
        animals.forEach(animal -> {
            animal.move(point);
        });
    }

    private void pairAction(ArrayList<Organism> organisms, ArrayList<Animal> animals) {
        animals.forEach(animal -> {
            animal.pair(animals);
            ArrayList<Animal> newCreatedAnimals = animal.pair(animals);
            organisms.addAll(newCreatedAnimals);
        });
    }

    private void eatAction(ArrayList<Organism> organisms, ArrayList<Animal> animals) {
        animals.forEach(animal -> {
            animal.eat(organisms);
        });
        cleanDeadOrganism(organisms);
    }

    private ArrayList<Animal> getAnimalsOnPoint(ArrayList<Organism> organisms) {
        return organisms.stream()
                .filter(currentOrganism -> currentOrganism instanceof Animal)
                .map(currentOrganism -> (Animal) currentOrganism)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Plant> getPlantsOnPoint(ArrayList<Organism> organisms) {
        return organisms.stream()
                .filter(currentOrganism -> currentOrganism instanceof Plant)
                .map(currentOrganism -> (Plant) currentOrganism)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Organism> cleanDeadOrganism(ArrayList<Organism> organismsOnPoint) {
        Iterator<Organism> iterator = organismsOnPoint.iterator();
        while (iterator.hasNext()) {
            Organism organism = iterator.next();
            if (!organism.isAlive()) {
                iterator.remove();
            }
        }
        return organismsOnPoint;
    }
}
