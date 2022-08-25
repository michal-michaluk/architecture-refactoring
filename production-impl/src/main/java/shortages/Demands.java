package shortages;

import java.time.LocalDate;
import java.util.Map;

public class Demands {
    private final Map<LocalDate, DailyDemand> demands;

    public Demands(Map<LocalDate, DailyDemand> demands) {
        this.demands = demands;
    }

    public boolean hasDemandsFor(LocalDate day) {
        return demands.containsKey(day);
    }

    public DailyDemand getDemand(LocalDate day) {
        if (hasDemandsFor(day)) {
            return demands.get(day);
        }
        return null;
    }

    public static class DailyDemand {
        private final long level;
        private final LevelOnDeliveryCalculation strategy;

        public DailyDemand(long level, LevelOnDeliveryCalculation schema) {
            this.level = level;
            this.strategy = schema;
        }

        public long calculateLevelOnDelivery(long level, long produced) {
            return strategy.levelOnDelivery(level, produced, this.level);
        }

        public long getLevel() {
            return level;
        }
    }
}
