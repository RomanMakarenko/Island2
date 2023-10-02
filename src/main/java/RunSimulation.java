import life.world.AllDeadException;
import life.statistic.Statistic;
import life.world.World;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RunSimulation {
    public static void main(String[] args) {
        World world = new World();
        ScheduledExecutorService animalsScheduler = Executors.newScheduledThreadPool(2);
        ScheduledExecutorService plantsScheduler = Executors.newScheduledThreadPool(1);
        animalsScheduler.scheduleAtFixedRate(() -> {
            try {
                System.out.println("Day number = " + world.iterationCounter);
                Statistic iterationStat = world.makeIteration();
                iterationStat.printStatistic();
                if (iterationStat.getFullNumberOfAnimalsOnStartOfIteration() == 0) {
                    throw new AllDeadException("All animals are dead");
                }
            } catch (AllDeadException e) {
                System.out.println(e + " within " + world.iterationCounter + " days");
                animalsScheduler.shutdown();
            } catch (Exception e) {
                System.out.println("In simulation left only 1 kind of organism type " + world.iterationCounter + " days");
                animalsScheduler.shutdown();
            }
        }, 0, World.WIDTH * World.HEIGHT, TimeUnit.SECONDS);
        plantsScheduler.scheduleAtFixedRate(() -> {
            world.makePlantsActions();
        }, 1, World.WIDTH * World.HEIGHT, TimeUnit.SECONDS);

        Thread stopThread = new Thread(() -> {
            try {
                Thread.sleep(World.WIDTH * World.HEIGHT * 10000);
                plantsScheduler.shutdown();
                animalsScheduler.shutdown();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        stopThread.start();
    }
}
