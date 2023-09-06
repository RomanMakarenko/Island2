package life.world;

import life.Animal;
import life.Organism;
import life.Plant;
import life.statistic.Statistic;
import life.statistic.StatisticOnPoint;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class World {
    public static final int WIDTH = 2;
    public static final int HEIGHT = 3;
    private Island island;
    ScheduledExecutorService executorService = Executors.newScheduledThreadPool(2);

    public World() {
        island = Island.getInstance(WIDTH, HEIGHT);
    }

    public Statistic makeIteration() throws AllDeadException {
        Statistic statistic = new Statistic();
        HashMap<Point, ArrayList<Organism>> islandMap = island.getLifeOnIsland();
        if (islandMap.size() == 0) {
            throw new AllDeadException("Everyone is dead");
        }
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
            ArrayList<Plant> plants = island.getPlantsOnPoint(point);
            growthAction(plants);
            statistic.addStatisticsOnPoint(statisticOnPoint);
        }
        island.refreshMap();
        return statistic;
    }

    public Statistic makeAnimalActions() throws AllDeadException {
        Statistic statistic = new Statistic();
        HashMap<Point, ArrayList<Organism>> islandMap = island.getLifeOnIsland();
        if (islandMap.size() == 0) {
            throw new AllDeadException("Everyone is dead");
        }
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
        return statistic;
    }

    public void makePlantsActions() {

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
        animals.forEach(animal -> {
            animal.eat();
        });
    }
}
