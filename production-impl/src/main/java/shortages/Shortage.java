package shortages;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class Shortage {

    private final String productRefNo;
    private final List<DailyShortage> shortages;

    public Shortage(String productRefNo, List<DailyShortage> shortages) {
        this.productRefNo = productRefNo;
        this.shortages = shortages;
    }

    public static Shortage empty(String productRefNo) {
        return new Shortage(productRefNo, new ArrayList<>());
    }

    public void add(LocalDate day, long missing) {
        shortages.add(new DailyShortage(
                productRefNo,
                LocalDate.now(),
                day,
                Math.abs(missing)
        ));
    }

    public boolean isEmpty() {
        return shortages.isEmpty();
    }

    public boolean isDifferentThan(Shortage previous) {
        return !shortages.isEmpty() && !shortages.equals(previous.shortages);
    }

    public boolean firstBefore(LocalDate date) {
        return shortages.get(0).isBefore(date);
    }

    public <T> List<T> mapEach(Function<DailyShortage, T> mapper) {
        return shortages.stream().map(mapper).toList();
    }

    public record DailyShortage(
            String refNo,
            LocalDate found,
            LocalDate atDay,
            long missing
    ) {
        boolean isBefore(LocalDate date) {
            return atDay.isBefore(date);
        }
    }
}
