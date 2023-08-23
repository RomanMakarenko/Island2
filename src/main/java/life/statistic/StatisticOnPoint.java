package life.statistic;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class StatisticOnPoint {
    private int numberOfOrganismsOnStartOfIteration;
    private int numberOfAnimalsOnStartOfIteration;
    private int numberOfOrganismsAfterEat;
    private int numberOfOrganismsAfterPair;

    public StatisticOnPoint() {
    }
}
