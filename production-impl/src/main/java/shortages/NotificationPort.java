package shortages;

public interface NotificationPort {
    void alertPlanner(Shortage shortages);

    void softNotifyPlanner(Shortage shortages);

    void markOnPlan(Shortage shortages);
}
