package life.world;

import life.Animal;
import life.Organism;
import life.Plant;
import life.statistic.Statistic;
import life.statistic.StatisticOnPoint;

import java.util.*;

public class World {
    public static final int WIDTH = 3;
    public static final int HEIGHT = 3;
    private Island island;
    public int iterationCounter = 0;

    public World() {
        island = Island.getInstance(WIDTH, HEIGHT);
    }

    public Statistic makeIteration() {
        Statistic statistic = new Statistic();
        HashMap<Point, ArrayList<Organism>> islandMap = island.getLifeOnIsland();
        for (Map.Entry<Point, ArrayList<Organism>> entry : islandMap.entrySet()) {
            Point point = entry.getKey();
            ArrayList<Organism> organisms = entry.getValue();
            StatisticOnPoint statisticOnPoint = new StatisticOnPoint();
            statisticOnPoint.setNumberOfOrganismsOnStartOfIteration(organisms.size());
            Collections.shuffle(organisms);
            ArrayList<Animal> animals = island.getAnimalsOnPoint(point);
            statisticOnPoint.setNumberOfAnimalsOnStartOfIteration(animals.size());
            eatAction(animals);
            island.cleanDeadOrganism(point);
            statisticOnPoint.setNumberOfOrganismsAfterEat(organisms.size());
            animals = island.getAnimalsOnPoint(point);
            pairAction(animals);
            statisticOnPoint.setNumberOfOrganismsAfterPair(organisms.size());
            animals = island.getAnimalsOnPoint(point);
            movingAction(animals);
            statistic.addStatisticsOnPoint(statisticOnPoint);
        }
        island.refreshMap();
        iterationCounter++;
        return statistic;
    }

    public void makePlantsActions() {
        HashMap<Point, ArrayList<Organism>> islandMap = island.getLifeOnIsland();
        for (Map.Entry<Point, ArrayList<Organism>> entry : islandMap.entrySet()) {
            Point point = entry.getKey();
            ArrayList<Plant> plants = island.getPlantsOnPoint(point);
            growthAction(plants);
        }
    }

    private void growthAction(ArrayList<Plant> plants) {
        plants.forEach(plant -> {
            plant.growth();
        });
    }

    private void movingAction(ArrayList<Animal> animals) {
        animals.forEach(animal -> {
            animal.move();
        });
    }

    private void pairAction(ArrayList<Animal> animals) {
        animals.forEach(animal -> {
            animal.pair();
        });
    }

    private void eatAction(ArrayList<Animal> animals) {
        Iterator<Animal> iterator = animals.iterator();
        while (iterator.hasNext()) {
            Animal animal = iterator.next();
            animal.eat();
            if (animal.isAlive()) {
                iterator.remove();
            }
        }
    }
}
