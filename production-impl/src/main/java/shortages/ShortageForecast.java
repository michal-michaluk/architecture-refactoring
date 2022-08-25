package shortages;

import java.time.LocalDate;

class ShortageForecast {
    private final String productRefNo;
    private final DateRange dates;
    private final ProductionOutputs outputs;
    private final Demands demandsPerDay;
    private final WarehouseStock stock;

    public ShortageForecast(String productRefNo, DateRange dates, ProductionOutputs outputs, Demands demandsPerDay, WarehouseStock stock) {
        this.productRefNo = productRefNo;
        this.dates = dates;
        this.outputs = outputs;
        this.demandsPerDay = demandsPerDay;
        this.stock = stock;
    }

    public Shortage predictShortages() {
        Shortage shortage = Shortage.empty(productRefNo);

        long level = stock.level();
        for (LocalDate day : dates) {
            if (!demandsPerDay.hasDemandsFor(day)) {
                level += outputs.getOutputs(day);
                continue;
            }
            long produced = outputs.getOutputs(day);

            Demands.DailyDemand demand = demandsPerDay.getDemand(day);
            long levelOnDelivery = demand.calculateLevelOnDelivery(level, produced);

            if (levelOnDelivery < 0) {
                shortage.add(day, levelOnDelivery);
            }
            long endOfDayLevel = level + produced - demand.getLevel();
            level = endOfDayLevel >= 0 ? endOfDayLevel : 0;
        }
        return shortage;
    }
}
