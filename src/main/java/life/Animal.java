package life;

import com.fasterxml.jackson.databind.JsonNode;
import dataManager.ConfigurationManager;
import life.behavior.Direction;
import life.behavior.Eating;
import life.behavior.Moving;
import life.behavior.Pairing;
import life.world.Island;
import life.world.Point;
import life.world.World;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Getter
@Setter
@EqualsAndHashCode(callSuper = true)
public abstract class Animal extends Organism implements Eating, Moving, Pairing {
    protected int maxSpeed;
    protected double mealSize;
    protected double eatenSize;
    protected boolean isPaired;
    protected int chanceForPairing;
    protected int endurance;
    private int DEFAULT_ENDURANCE;

    protected void loadConfig(String animalType) {
        ConfigurationManager configurationManager = new ConfigurationManager();
        JsonNode classNode = configurationManager.getConfigNode(animalType);
        if (classNode != null) {
            isAlive = true;
            weight = classNode.get("weight").asDouble();
            maxPopulationSize = classNode.get("maxPopulationSize").asInt();
            maxSpeed = classNode.get("maxSpeed").asInt();
            mealSize = classNode.get("mealSize").asDouble();
            chanceForPairing = classNode.get("chanceForPairing").asInt();
            endurance = classNode.get("endurance").asInt();
            DEFAULT_ENDURANCE = endurance;
            JsonNode chanceForLootNode = classNode.get("chanceForLoot");
            if (chanceForLootNode != null) {
                chanceForLootNode.fields().forEachRemaining(entry -> {
                    String animal = entry.getKey();
                    int chance = entry.getValue().asInt();
                    chanceForLoot.put(animal, chance);
                });
            } else {
                System.out.println("Missed chanceForLoot block");
            }
        }
    }

    @Override
    public void eat() {
        organismsListOnTheSamePoint = Island.instance.getOrganismListOnPoint(new Point(xAfterMove, yAfterMove));
        Collections.shuffle(organismsListOnTheSamePoint);
        List<String> potentialFoodListInIteration = this.getPotentialIterationFoodList();
        potentialFoodListInIteration.stream().forEach(food -> {
            for (Organism organism : organismsListOnTheSamePoint) {
                if (organism.getORGANISM_TYPE().equals(food) && organism.isAlive() && this.getEatenSize() < this.getMealSize()) {
                    organism.setAlive(false);
                    if (this.getMealSize() < organism.getWeight()) {
                        this.setEatenSize(this.getMealSize());
                    } else {
                        this.setEatenSize(Math.min(this.getEatenSize() + organism.getWeight(), this.getMealSize()));
                    }
                    this.setEndurance(DEFAULT_ENDURANCE);
                    break;
                }
            }
        });
        this.setEndurance(this.getEndurance() - 1);
        if (this.endurance < 0) {
            this.setAlive(false);
        }
    }

    @Override
    public ArrayList<Animal> pair(ArrayList<Animal> animalsOnPoint) {
        if (this.isPaired() || !this.wantToPairing()) {
            return new ArrayList<>();
        }
        Collections.shuffle(animalsOnPoint);
        ArrayList<Animal> newCreatedAnimals = new ArrayList<>();
        animalsOnPoint.forEach(comparedAnimal -> {
            if (
                    comparedAnimal != this
                    && !comparedAnimal.isPaired()
                    && !this.isPaired()
                    && comparedAnimal.getORGANISM_TYPE().equals(this.getORGANISM_TYPE())
            ) {
                this.setPaired(true);
                comparedAnimal.setPaired(true);
                newCreatedAnimals.add((Animal) this.createClone());
            }
        });
        return newCreatedAnimals;
    }

    private boolean wantToPairing() {
        return ThreadLocalRandom.current().nextInt(
                0,
                100
        ) <= this.getChanceForPairing();
    }

    @Override
    public void move() {
        if (this.getMaxSpeed() == 0) {
            return;
        }
        int distance = ThreadLocalRandom.current().nextInt(0, this.getMaxSpeed());
        Point destinationPoint = new Point(this.getXAfterMove(), this.getYAfterMove());
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
        this.setXAfterMove(destinationPoint.getX());
        this.setYAfterMove(destinationPoint.getY());
    }

    private boolean isValidMove(Point point, Direction move) {
        if (
                (point.getX() == 0 && move == Direction.LEFT)
                || (point.getX() == World.WIDTH - 1 && move == Direction.RIGHT)
                || (point.getY() == 0 && move == Direction.DOWN)
                || (point.getY() == World.HEIGHT - 1 && move == Direction.UP)) {
            return false;
        } else {
            return true;
        }
    }
}
