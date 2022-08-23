package infrastructure;

import dao.ProductionDao;
import entities.ProductionEntity;
import shortages.ProductionOutputs;
import shortages.ProductionPort;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

public class ProductionAdapter implements ProductionPort {
    private final ProductionDao productionDao;

    public ProductionAdapter(ProductionDao productionDao) {
        this.productionDao = productionDao;
    }

    @Override
    public ProductionOutputs get(String productRefNo, LocalDateTime time) {
        return new ProductionOutputs(productionDao.findFromTime(productRefNo, time).stream()
                .collect(Collectors.groupingBy(
                        production -> production.getStart().toLocalDate(),
                        Collectors.summingLong(ProductionEntity::getOutput)
                )));
    }
}
