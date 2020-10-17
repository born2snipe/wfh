package wfh.status;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class StatusTracker {
    private String currentStatus;
    private CurrentStatusAccessor currentStatusAccessor;

    public StatusTracker(CurrentStatusAccessor currentStatusAccessor) {
        this.currentStatusAccessor = currentStatusAccessor;
    }

    @PostConstruct
    public void init() {
        currentStatus = currentStatusAccessor.findCurrentStatus().orElse(CannedActions.DONE_FOR_THE_DAY.name());
    }

    public void changeStatusTo(String newStatus) {
        System.out.println("newStatus = " + newStatus);
    }

    public String getCurrentStatus() {
        return currentStatus;
    }
}
