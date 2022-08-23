package shortages;

import java.time.LocalDateTime;

public interface ProductionPort {
    ProductionOutputs get(String productRefNo, LocalDateTime atStartOfDay);
}
