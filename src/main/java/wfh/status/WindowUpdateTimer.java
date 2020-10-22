package wfh.status;

import org.springframework.stereotype.Component;
import wfh.gui.MainWindow;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Timer;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.Executors.newSingleThreadScheduledExecutor;
import static java.util.concurrent.TimeUnit.MILLISECONDS;

@Component
public class WindowUpdateTimer {
    private final MainWindow window;
    private final Timer timer = new Timer();
    private final ScheduledExecutorService es = newSingleThreadScheduledExecutor();

    public WindowUpdateTimer(MainWindow window) {
        this.window = window;
    }

    @PostConstruct
    public void startUpdating() {
        es.scheduleAtFixedRate(window::updateElapsedTimes, 0, 10, MILLISECONDS);
    }

    @PreDestroy
    public void stopUpdating() {
        es.shutdown();
        while (!es.isTerminated()) {
            pause();
        }
    }

    private void pause() {
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {

        }
    }


}
