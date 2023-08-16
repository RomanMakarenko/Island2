package life.world;

import life.Animal;
import life.Organism;
import life.behavior.Direction;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

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
            ArrayList<Animal> animals = getAnimalsOnPoint(organisms);
            eatAction(organisms, animals);
            animals = getAnimalsOnPoint(organisms);
            pairAction(organisms, animals);
            animals = getAnimalsOnPoint(organisms);
            animals.forEach(animal -> {
                int distance = ThreadLocalRandom.current().nextInt(0, animal.getMaxSpeed());
                Point destinationPoint = new Point(point.getX(), point.getY());
                while (distance > 0) {
                    Direction currentDirection = Direction.getRandomDirection();
                    if (isValidMove(destinationPoint, currentDirection)) {
                        switch (currentDirection) {
                            case DOWN:
                                destinationPoint.setY(destinationPoint.getY() - 1);
                                break;
                            case RIGHT:
                                destinationPoint.setX(destinationPoint.getX() + 1);
                                break;
                            case UP:
                                destinationPoint.setY(destinationPoint.getY() + 1);
                                break;
                            case LEFT:
                                destinationPoint.setX(destinationPoint.getX() - 1);
                                break;
                        }
                        distance--;
                    }
                }
                animal.setXAfterMove(destinationPoint.getX());
                animal.setYAfterMove(destinationPoint.getY());
                System.out.println();
            });
            System.out.println();
        }

        move();

    }

    private boolean isValidMove(Point point, Direction move) {
        if (
            (point.getX() == 0 && move == Direction.LEFT)
            || (point.getX() == WIDTH - 1 && move == Direction.RIGHT)
            || (point.getY() == 0 && move == Direction.DOWN)
            || (point.getY() == HEIGHT - 1 && move == Direction.UP)) {
            return false;
        } else {
            return true;
        }
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

    private void move() {

    }

}
