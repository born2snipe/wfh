package wfh.gui.status;

import org.springframework.stereotype.Component;

@Component
public class StatusTracker {
    public void changeStatusTo(String newStatus) {
        System.out.println("newStatus = " + newStatus);
    }
}
