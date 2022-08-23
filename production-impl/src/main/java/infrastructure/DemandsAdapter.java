package infrastructure;

import dao.DemandDao;
import entities.DemandEntity;
import shortages.DemandPort;
import shortages.Demands;
import tools.Util;

import java.time.LocalDate;
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
                                Util.getLevel(demand),
                                Util.getDeliverySchema(demand)
                        )
                )));
    }
}
