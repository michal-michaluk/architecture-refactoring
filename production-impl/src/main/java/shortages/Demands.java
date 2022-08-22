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

    public long getLevel(LocalDate day) {
        if (hasDemandsFor(day)) {
            return Util.getLevel(demands.get(day));
        } else {
            return 0;
        }
    }

    public boolean hasDeliverySchema(LocalDate day, DeliverySchema schema) {
        if (hasDemandsFor(day)) {
            return Util.getDeliverySchema(demands.get(day)) == schema;
        } else {
            return false;
        }
    }
}
