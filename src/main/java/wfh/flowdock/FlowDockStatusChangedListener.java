package wfh.flowdock;

import org.springframework.stereotype.Component;
import wfh.status.RunNotOnEDTStatusChangeListener;
import wfh.status.Status;

@Component
public class FlowDockStatusChangedListener extends RunNotOnEDTStatusChangeListener {
    @Override
    protected void performStatusChange(Status previousStatus, Status newStatus) {
        System.out.println(Thread.currentThread().getName() + " Notify flowdock - " + newStatus);

    }
}
