package wfh.status;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class StatusTracker {
    private String currentStatus;
    private CurrentStatusAccessor currentStatusAccessor;
    private final ArrayList<StatusChangedListener> listeners = new ArrayList<>();

    public StatusTracker(CurrentStatusAccessor currentStatusAccessor, List<StatusChangedListener> listeners) {
        this.currentStatusAccessor = currentStatusAccessor;
        this.listeners.addAll(listeners);
    }

    @PostConstruct
    public void init() {
        currentStatus = currentStatusAccessor.findCurrentStatus().orElse(CannedActions.DONE_FOR_THE_DAY.name());
    }

    public void changeStatusTo(String newStatus) {
        for (StatusChangedListener listener : listeners) {
            listener.statusChanged(currentStatus, newStatus);
        }
        currentStatus = newStatus;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }
}
