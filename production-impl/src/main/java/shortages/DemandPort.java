package shortages;

import java.time.LocalDate;

public interface DemandPort {
    Demands get(String productRefNo, LocalDate today);
}
