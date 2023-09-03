package life.world;

import life.Animal;
import life.Organism;
import life.Plant;
import life.statistic.Statistic;
import life.statistic.StatisticOnPoint;

import java.util.*;
import java.util.stream.Collectors;

public class World {
    public static final int WIDTH = 2;
    public static final int HEIGHT = 3;
    private Island island;

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
            ArrayList<Animal> animals = getAnimalsOnPoint(organisms);
            statisticOnPoint.setNumberOfAnimalsOnStartOfIteration(animals.size());
            eatAction(animals);
            island.cleanDeadOrganism(point);
            statisticOnPoint.setNumberOfOrganismsAfterEat(organisms.size());
            animals = getAnimalsOnPoint(organisms);
            pairAction(animals);
            statisticOnPoint.setNumberOfOrganismsAfterPair(organisms.size());
            animals = getAnimalsOnPoint(organisms);
            movingAction(animals);
            statistic.addStatisticsOnPoint(statisticOnPoint);
        }
        island.refreshMap();
        return statistic;
    }

    private void growthAction(ArrayList<Organism> organisms, Point point, ArrayList<Plant> plants) {
        plants.forEach(plant -> {
            ArrayList<Plant> newCreatedPlants = plant.growth(point, plants);
            organisms.addAll(newCreatedPlants);
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

//    private void cleanDeadOrganism(Point point) {
//        Iterator<Organism> iterator = islandMap.get(point).iterator();
//        while (iterator.hasNext()) {
//            Organism organism = iterator.next();
//            if (!organism.isAlive()) {
//                iterator.remove();
//            }
//        }
//    }

    private boolean isContainsDeadOrganism(ArrayList<Organism> organismsOnPoint) {
        Iterator<Organism> iterator = organismsOnPoint.iterator();
        while (iterator.hasNext()) {
            Organism organism = iterator.next();
            if (!organism.isAlive()) {
                return true;
            }
        }
        return false;
    }
}
