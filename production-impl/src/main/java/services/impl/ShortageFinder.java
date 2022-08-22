package services.impl;

import dao.DemandDao;
import dao.ProductionDao;
import entities.ShortageEntity;
import external.CurrentStock;
import shortages.Demands;
import shortages.ProducitonOutputs;

import java.time.LocalDate;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class ShortageFinder {

    private final DemandDao demandDao;
    private final ProductionDao productionDao;

    public ShortageFinder(DemandDao demandDao, ProductionDao productionDao) {
        this.demandDao = demandDao;
        this.productionDao = productionDao;
    }

    /**
     * Production at day of expected delivery is quite complex:
     * We are able to produce and deliver just in time at same day
     * but depending on delivery time or scheme of multiple deliveries,
     * we need to plan properly to have right amount of parts ready before delivery time.
     * <p/>
     * Typical schemas are:
     * <li>Delivery at prod day start</li>
     * <li>Delivery till prod day end</li>
     * <li>Delivery during specified shift</li>
     * <li>Multiple deliveries at specified times</li>
     * Schema changes the way how we calculate shortages.
     * Pick of schema depends on customer demand on daily basis and for each product differently.
     * Some customers includes that information in callof document,
     * other stick to single schema per product. By manual adjustments of demand,
     * customer always specifies desired delivery schema
     * (increase amount in scheduled transport or organize extra transport at given time)
     */
    public List<ShortageEntity> findShortages(String productRefNo, LocalDate today, int daysAhead, CurrentStock stock) {

        List<LocalDate> dates = Stream.iterate(today, date -> date.plusDays(1))
                .limit(daysAhead)
                .toList();

        ProducitonOutputs outputs = new ProducitonOutputs(productionDao.findFromTime(productRefNo, today.atStartOfDay()));
        Demands demandsPerDay = new Demands(demandDao.findFrom(today.atStartOfDay(), productRefNo));

        long level = stock.getLevel();

        List<ShortageEntity> gap = new LinkedList<>();
        for (LocalDate day : dates) {
            if (!demandsPerDay.hasDemandsFor(day)) {
                level += outputs.getOutputs(day);
                continue;
            }
            long produced = outputs.getOutputs(day);

            Demands.DailyDemand demand = demandsPerDay.getDemand(day);
            long levelOnDelivery = demand.calculateLevelOnDelivery(level, produced);

            if (levelOnDelivery < 0) {
                ShortageEntity entity = new ShortageEntity();
                entity.setRefNo(productRefNo);
                entity.setFound(LocalDate.now());
                entity.setAtDay(day);
                entity.setMissing(-levelOnDelivery);
                gap.add(entity);
            }
            long endOfDayLevel = level + produced - demand.getLevel();
            level = endOfDayLevel >= 0 ? endOfDayLevel : 0;
        }
        return gap;
    }
}
