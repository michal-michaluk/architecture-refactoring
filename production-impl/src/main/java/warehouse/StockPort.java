package warehouse;

import shortages.WarehouseStock;

public interface StockPort {
    WarehouseStock getStock(String productRefNo);
}
