package infrastructure;

import dao.ShortageDao;
import shortages.Shortage;
import shortages.ShortagePort;

public class ShortageAdapter implements ShortagePort {

    private final ShortageDao shortageDao;

    public ShortageAdapter(ShortageDao shortageDao) {
        this.shortageDao = shortageDao;
    }

    @Override
    public Shortage get(String productRefNo) {
        return new Shortage(productRefNo, shortageDao.getForProduct(productRefNo));
    }

    @Override
    public void save(Shortage shortages) {
        shortageDao.save(shortages.getShortages());
    }

    @Override
    public void delete(String productRefNo) {
        shortageDao.delete(productRefNo);
    }
}
