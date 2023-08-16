package life.behavior;

import life.Organism;

import java.util.ArrayList;

public interface Eating {
    public ArrayList<Organism> eat(ArrayList<Organism> organismsOnPoint);
}
