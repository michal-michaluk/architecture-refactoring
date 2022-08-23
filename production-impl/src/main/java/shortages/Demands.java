package shortages;

import enums.DeliverySchema;

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
        private final DeliverySchema schema;

        public DailyDemand(long level, DeliverySchema schema) {
            this.level = level;
            this.schema = schema;
        }

        public long calculateLevelOnDelivery(long level, long produced) {
            long levelOnDelivery;
            if (hasDeliverySchema(DeliverySchema.atDayStart)) {
                levelOnDelivery = level - getLevel();
            } else if (hasDeliverySchema(DeliverySchema.tillEndOfDay)) {
                levelOnDelivery = level - getLevel() + produced;
            } else if (hasDeliverySchema(DeliverySchema.every3hours)) {
                // TODO WTF ?? we need to rewrite that app :/
                throw new UnsupportedOperationException();
            } else {
                // TODO implement other variants
                throw new UnsupportedOperationException();
            }
            return levelOnDelivery;
        }

        public long getLevel() {
            return level;
        }

        private boolean hasDeliverySchema(DeliverySchema schema) {
            return this.schema == schema;
        }
    }
}
