import life.world.AllDeadException;
import life.statistic.Statistic;
import life.world.World;

public class RunSimulation {
    public static void main(String[] args) {
        World world = new World();
        int iterationCounter = 0;
        while(true) {
            try {
                iterationCounter++;
                System.out.printf("Day number = %d", iterationCounter);
                System.out.println();
                Statistic iterationStat = world.makeIteration();
                iterationStat.printStatistic();
                if (iterationStat.getFullNumberOfAnimalsOnStartOfIteration() == 0) {
                    throw new AllDeadException("All animals are dead");
                }
            } catch (AllDeadException e) {
                System.out.println(e + " within " + iterationCounter + " days");
                break;
            } catch (Exception e) {
                System.out.println("In simulation left only 1 kind of organism type " + iterationCounter + " days");
                break;
            }
        }
    }
}
