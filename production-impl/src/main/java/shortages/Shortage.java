package shortages;

import entities.ShortageEntity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Shortage {

    private final String productRefNo;
    private final List<ShortageEntity> shortages;

    public Shortage(String productRefNo, List<ShortageEntity> shortages) {
        this.productRefNo = productRefNo;
        this.shortages = shortages;
    }

    public static Shortage empty(String productRefNo) {
        return new Shortage(productRefNo, new ArrayList<>());
    }

    public void add(LocalDate day, long levelOnDelivery) {
        ShortageEntity entity = new ShortageEntity();
        entity.setRefNo(productRefNo);
        entity.setFound(LocalDate.now());
        entity.setAtDay(day);
        entity.setMissing(-levelOnDelivery);
        shortages.add(entity);
    }

    public boolean isEmpty() {
        return shortages.isEmpty();
    }

    public boolean isDifferentThan(Shortage previous) {
        return !shortages.isEmpty() && !shortages.equals(previous.shortages);
    }

    public boolean firstBefore(LocalDate date) {
        return shortages.get(0).getAtDay().isBefore(date);
    }

    public List<ShortageEntity> getShortages() {
        return shortages;
    }
}
