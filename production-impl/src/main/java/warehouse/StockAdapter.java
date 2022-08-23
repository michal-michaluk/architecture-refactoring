package warehouse;

import external.CurrentStock;
import external.StockService;
import shortages.WarehouseStock;

public class StockAdapter implements StockPort {

    private StockService stockService;

    @Override
    public WarehouseStock getStock(String productRefNo) {
        CurrentStock stock = stockService.getCurrentStock(productRefNo);
        return new WarehouseStock(stock.getLevel(), stock.getLocked());
    }
}
