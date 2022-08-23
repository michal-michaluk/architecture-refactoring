package shortages;

import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class DateRange implements Iterable<LocalDate> {
    private final List<LocalDate> dates;

    private DateRange(List<LocalDate> dates) {
        this.dates = dates;
    }

    public static DateRange of(LocalDate today, int daysAhead) {
        return new DateRange(Stream.iterate(today, date -> date.plusDays(1))
                .limit(daysAhead)
                .toList());
    }

    @Override
    public Iterator<LocalDate> iterator() {
        return dates.iterator();
    }
}
