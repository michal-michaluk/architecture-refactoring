package infrastructure;

import external.NotificationsService;
import services.impl.NotificationPort;
import shortages.Shortage;

public class NotificationAdapter implements NotificationPort {

    NotificationsService service;

    @Override
    public void alertPlanner(Shortage shortages) {
        service.alertPlanner(shortages.getShortages());
    }

    @Override
    public void softNotifyPlanner(Shortage shortages) {
        service.softNotifyPlanner(shortages.getShortages());
    }

    @Override
    public void markOnPlan(Shortage shortages) {
        service.markOnPlan(shortages.getShortages());
    }
}
