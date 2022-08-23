package shortages;

import external.JiraService;
import warehouse.StockPort;

import java.time.Clock;
import java.time.LocalDate;

public class ShortagesService {

    private final ShortagePort shortagePort;
    private final StockPort stockPort;
    private final ShortageFinder shortageFinder;

    private final NotificationPort notificationPort;
    private final JiraService jiraService;
    private final Clock clock;

    private final int confShortagePredictionDaysAhead;
    private final long confIncreaseQATaskPriorityInDays;

    public ShortagesService(ShortagePort shortagePort, StockPort stockPort, ShortageFinder shortageFinder, NotificationPort notificationPort, JiraService jiraService, Clock clock, int confShortagePredictionDaysAhead, long confIncreaseQATaskPriorityInDays) {
        this.shortagePort = shortagePort;
        this.stockPort = stockPort;
        this.shortageFinder = shortageFinder;
        this.notificationPort = notificationPort;
        this.jiraService = jiraService;
        this.clock = clock;
        this.confShortagePredictionDaysAhead = confShortagePredictionDaysAhead;
        this.confIncreaseQATaskPriorityInDays = confIncreaseQATaskPriorityInDays;
    }

    public void findShortages(String productRefNo) {
        LocalDate today = LocalDate.now(clock);
        WarehouseStock stock = stockPort.getStock(productRefNo);

        Shortage shortages = shortageFinder.findShortages(
                productRefNo,
                today, confShortagePredictionDaysAhead,
                stock
        );
        Shortage previous = shortagePort.get(productRefNo);
        // TODO REFACTOR: lookup for shortages -> ShortageFound / ShortagesGone
        if (shortages.isDifferentThan(previous)) {
            notificationPort.alertPlanner(shortages);
            // TODO REFACTOR: policy why to increase task priority
            if (stock.locked() > 0 &&
                    shortages.firstBefore(today.plusDays(confIncreaseQATaskPriorityInDays))) {
                jiraService.increasePriorityFor(productRefNo);
            }
            shortagePort.save(shortages);
        }
        if (shortages.isEmpty() && !previous.isEmpty()) {
            shortagePort.delete(productRefNo);
        }
    }
}
