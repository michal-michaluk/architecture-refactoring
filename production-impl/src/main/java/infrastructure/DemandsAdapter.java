package infrastructure;

import dao.DemandDao;
import demands.DemandEntity;
import enums.DeliverySchema;
import shortages.DemandPort;
import shortages.Demands;
import shortages.LevelOnDeliveryCalculation;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

public class DemandsAdapter implements DemandPort {

    private final DemandDao demandDao;

    public DemandsAdapter(DemandDao demandDao) {
        this.demandDao = demandDao;
    }

    @Override
    public Demands get(String productRefNo, LocalDate today) {
        return new Demands(demandDao.findFrom(today.atStartOfDay(), productRefNo).stream()
                .collect(Collectors.toMap(
                        DemandEntity::getDay,
                        demand -> new Demands.DailyDemand(
                                demand.getLevel(),
                                toStrategy(demand.getDeliverySchema())
                        )
                )));
    }


    private static LevelOnDeliveryCalculation toStrategy(DeliverySchema schema) {
        return Map.of(
                DeliverySchema.atDayStart, LevelOnDeliveryCalculation.atDayStart,
                DeliverySchema.tillEndOfDay, LevelOnDeliveryCalculation.tillEndOfDay
        ).getOrDefault(schema, LevelOnDeliveryCalculation.error);
    }
}
