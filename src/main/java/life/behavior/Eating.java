package life.behavior;

import life.Organism;

import java.util.ArrayList;

public interface Eating {
    ArrayList<Organism> eat(ArrayList<Organism> organismsOnPoint);
}
