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
        return ShortageTranslation.fromEntities(productRefNo, shortageDao.getForProduct(productRefNo));
    }

    @Override
    public void save(Shortage shortages) {
        shortageDao.save(ShortageTranslation.toEntities(shortages));
    }

    @Override
    public void delete(String productRefNo) {
        shortageDao.delete(productRefNo);
    }


}
