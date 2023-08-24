package life.statistic;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Statistic {
    private List<StatisticOnPoint> mapStatistic;

    public Statistic() {
        mapStatistic = new ArrayList<>();
    }

    public void addStatisticsOnPoint(StatisticOnPoint statisticOnPoint) {
        mapStatistic.add(statisticOnPoint);
    }

    private int getFullNumberOfOrganismsOnStartOfIteration() {
        return mapStatistic.stream()
                .mapToInt(StatisticOnPoint::getNumberOfOrganismsOnStartOfIteration)
                .sum();
    }

    public int getFullNumberOfAnimalsOnStartOfIteration() {
        return mapStatistic.stream()
                .mapToInt(StatisticOnPoint::getNumberOfAnimalsOnStartOfIteration)
                .sum();
    }

    private int getFullNumberOfPlantsOnStartOfIteration() {
        return this.getFullNumberOfOrganismsOnStartOfIteration() - this.getFullNumberOfAnimalsOnStartOfIteration();
    }

    private int getFullNumberOfOrganismsAfterEat() {
        return mapStatistic.stream()
                .mapToInt(StatisticOnPoint::getNumberOfOrganismsAfterEat)
                .sum();
    }

    private int getFullNumberOfBornOrganisms() {
        return mapStatistic.stream()
                .mapToInt(StatisticOnPoint::getNumberOfOrganismsAfterPair)
                .sum() - this.getFullNumberOfOrganismsAfterEat();
    }

    public void printStatistic() {
        System.out.println("=============================================================");
        System.out.println("Number of organisms on start of the iteration = " + this.getFullNumberOfOrganismsOnStartOfIteration());
        System.out.println("Number of animals on start of the iteration = " + this.getFullNumberOfAnimalsOnStartOfIteration());
        System.out.println("Number of plants on start of the iteration = " + this.getFullNumberOfPlantsOnStartOfIteration());
        System.out.println("Number of organisms after eat = " + this.getFullNumberOfOrganismsAfterEat());
        System.out.println("Number of born organisms = " + this.getFullNumberOfBornOrganisms());
        System.out.println("=============================================================");
    }
}
