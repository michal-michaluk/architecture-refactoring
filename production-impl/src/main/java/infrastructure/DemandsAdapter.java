package infrastructure;

import dao.DemandDao;
import shortages.DemandRepository;
import shortages.Demands;
import tools.Util;

import java.time.LocalDate;
import java.util.stream.Collectors;

public class DemandsAdapter implements DemandRepository {

    private final DemandDao demandDao;

    public DemandsAdapter(DemandDao demandDao) {
        this.demandDao = demandDao;
    }

    @Override
    public Demands get(String productRefNo, LocalDate today) {
        return new Demands(demandDao.findFrom(today.atStartOfDay(), productRefNo).stream()
                .collect(Collectors.toMap(
                        demand -> demand.getDay(),
                        demand -> new Demands.DailyDemand(
                                Util.getLevel(demand),
                                Util.getDeliverySchema(demand)
                        )
                )));
    }
}
