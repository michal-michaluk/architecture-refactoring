package shortages;

import entities.ProductionEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProducitonOutputs {
    private final Map<LocalDate, List<ProductionEntity>> outputs;

    public ProducitonOutputs(List<ProductionEntity> productions) {
        Map<LocalDate, List<ProductionEntity>> outputs = new HashMap<>();
        for (ProductionEntity production : productions) {
            if (!outputs.containsKey(production.getStart().toLocalDate())) {
                outputs.put(production.getStart().toLocalDate(), new ArrayList<>());
            }
            outputs.get(production.getStart().toLocalDate()).add(production);
        }
        this.outputs = outputs;
    }

    public long getOutputs(LocalDate day) {
        long sum = 0;
        for (ProductionEntity production : outputs.get(day)) {
            sum += production.getOutput();
        }
        return sum;
    }
}
