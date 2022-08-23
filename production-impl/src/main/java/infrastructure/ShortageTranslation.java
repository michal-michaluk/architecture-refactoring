package infrastructure;

import entities.ShortageEntity;
import shortages.Shortage;

import java.util.List;
import java.util.stream.Collectors;

public class ShortageTranslation {
    public static Shortage fromEntities(String productRefNo, List<ShortageEntity> entities) {
        return new Shortage(productRefNo, entities.stream()
                .map(entity -> new Shortage.DailyShortage(
                        entity.getRefNo(),
                        entity.getFound(),
                        entity.getAtDay(),
                        entity.getMissing()
                )).collect(Collectors.toList()));
    }

    public static List<ShortageEntity> toEntities(Shortage shortages) {
        return shortages.mapEach((Shortage.DailyShortage object) -> {
            ShortageEntity entity = new ShortageEntity();
            entity.setRefNo(object.refNo());
            entity.setFound(object.found());
            entity.setAtDay(object.atDay());
            entity.setMissing(object.missing());
            return entity;
        });
    }
}
