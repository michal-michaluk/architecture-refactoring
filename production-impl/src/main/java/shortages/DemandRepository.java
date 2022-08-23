package shortages;

import java.time.LocalDate;

public interface DemandRepository {
    Demands get(String productRefNo, LocalDate today);
}
