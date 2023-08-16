package life.behavior;

import life.Animal;
import life.Organism;

import java.util.ArrayList;

public interface Pairing {
    public ArrayList<Animal> pair(ArrayList<Animal> organismsOnPoint);
}
