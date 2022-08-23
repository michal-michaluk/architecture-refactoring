package shortages;

public interface ShortagePort {
    Shortage get(String productRefNo);

    void save(Shortage shortages);

    void delete(String productRefNo);
}
