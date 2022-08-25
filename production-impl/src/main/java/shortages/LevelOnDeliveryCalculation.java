package shortages;

public interface LevelOnDeliveryCalculation {
    LevelOnDeliveryCalculation atDayStart = (long level, long produced, long demand) -> level - demand;
    LevelOnDeliveryCalculation tillEndOfDay = (long level, long produced, long demand) -> level - demand + produced;
    LevelOnDeliveryCalculation error = (long level, long produced, long demand) -> {
        throw new UnsupportedOperationException();
    };

    long levelOnDelivery(long level, long produced, long demand);
}
