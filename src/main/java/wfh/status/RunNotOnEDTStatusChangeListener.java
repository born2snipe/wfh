package wfh.status;

import javax.annotation.PreDestroy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class RunNotOnEDTStatusChangeListener implements StatusChangedListener {
    private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2);

    @PreDestroy
    public void killThreadPool() {
        synchronized (EXECUTOR_SERVICE) {
            EXECUTOR_SERVICE.shutdown();

            while (!EXECUTOR_SERVICE.isTerminated()) {
                pause();
            }
        }
    }

    private void pause() {
        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {

        }
    }

    @Override
    public final void statusChanged(String previousStatus, String newStatus) {
        EXECUTOR_SERVICE.submit(() -> performStatusChange(previousStatus, newStatus));
    }

    protected abstract void performStatusChange(String previousStatus, String newStatus);
}
