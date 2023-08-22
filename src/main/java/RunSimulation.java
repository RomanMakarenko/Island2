import life.world.AllDeadException;
import life.world.World;

public class RunSimulation {
    public static void main(String[] args) {
        World world = new World();
        int iterationCounter = 0;
        while(true) {
            try {
                iterationCounter++;
                System.out.printf("Day number = d%", iterationCounter);
                System.out.println("=============================================================");
                world.makeIteration();
                System.out.println("=============================================================");
            } catch (AllDeadException e) {
                System.out.println(e + " within " + iterationCounter + " days");
                break;

            }
        }
    }
}
