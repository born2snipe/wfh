package wfh.status;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static wfh.status.Status.DONE_FOR_THE_DAY;

@Component
public class StatusTracker {
    private Status currentStatus;
    private final CurrentStatusAccessor currentStatusAccessor;
    private final ArrayList<StatusChangedListener> listeners = new ArrayList<>();

    public StatusTracker(CurrentStatusAccessor currentStatusAccessor, List<StatusChangedListener> listeners) {
        this.currentStatusAccessor = currentStatusAccessor;
        this.listeners.addAll(listeners);
    }

    @PostConstruct
    public void init() {
        currentStatus = currentStatusAccessor.findCurrentStatus().orElse(DONE_FOR_THE_DAY);
    }

    public void changeStatusTo(Status newStatus) {
        if (!currentStatus.equals(newStatus)) {
            for (StatusChangedListener listener : listeners) {
                listener.statusChanged(currentStatus, newStatus);
            }
            currentStatus = newStatus;
        }
    }

    public Status getCurrentStatus() {
        return currentStatus;
    }
}
