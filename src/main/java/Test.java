import dataManager.DataCollector;
import life.Animal;
import life.Organism;
import life.predators.Fox;
import life.predators.Wolf;
import life.world.Island;

import java.util.List;

public class Test {
    public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        Wolf wolf = new Wolf();
        Wolf wolf2 = new Wolf();
        Fox fox = new Fox();
        System.out.println(wolf.getMaxSpeed());
        System.out.println(fox.getMaxSpeed());
        System.out.println(wolf2.getMaxSpeed());
        Island island = new Island();
        island.generateLifeOnIsland();
        System.out.println();
//        DataCollector dataCollector = new DataCollector();
//        List<Organism> organismList = dataCollector.getOrganismList();
//        for (Organism organism: organismList) {
//            if (organism instanceof Animal) {
//               Animal animal = (Animal) organism;
//                System.out.println(animal.getMealSize());
//            }
//        }

    }
}
