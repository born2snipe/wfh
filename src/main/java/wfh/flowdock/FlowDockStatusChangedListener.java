package wfh.flowdock;

import org.springframework.stereotype.Component;
import wfh.status.RunNotOnEDTStatusChangeListener;

@Component
public class FlowDockStatusChangedListener extends RunNotOnEDTStatusChangeListener {
    @Override
    protected void performStatusChange(String previousStatus, String newStatus) {
        System.out.println(Thread.currentThread().getName() + " Notify flowdock - " + newStatus);

    }
}
