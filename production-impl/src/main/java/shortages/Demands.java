package shortages;

import entities.DemandEntity;
import enums.DeliverySchema;
import tools.Util;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Demands {
    private final Map<LocalDate, DemandEntity> demands;

    public Demands(List<DemandEntity> demands) {
        Map<LocalDate, DemandEntity> demandsPerDay = new HashMap<>();
        for (DemandEntity demand : demands) {
            demandsPerDay.put(demand.getDay(), demand);
        }
        this.demands = demandsPerDay;
    }

    public boolean hasDemandsFor(LocalDate day) {
        return demands.containsKey(day);
    }

    public DailyDemand getDemand(LocalDate day) {
        if (hasDemandsFor(day)) {
            return new DailyDemand(demands.get(day));
        }
        return null;
    }

    public static class DailyDemand {
        private final DemandEntity demand;

        public DailyDemand(DemandEntity demand) {
            this.demand = demand;
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
            return Util.getLevel(demand);
        }

        private boolean hasDeliverySchema(DeliverySchema schema) {
            return Util.getDeliverySchema(demand) == schema;
        }
    }
}
