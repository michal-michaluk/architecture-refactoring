package infrastructure;

import external.NotificationsService;
import shortages.NotificationPort;
import shortages.Shortage;

public class NotificationAdapter implements NotificationPort {

    NotificationsService service;

    @Override
    public void alertPlanner(Shortage shortages) {
        service.alertPlanner(ShortageTranslation.toEntities(shortages));
    }

    @Override
    public void softNotifyPlanner(Shortage shortages) {
        service.softNotifyPlanner(ShortageTranslation.toEntities(shortages));
    }

    @Override
    public void markOnPlan(Shortage shortages) {
        service.markOnPlan(ShortageTranslation.toEntities(shortages));
    }
}
