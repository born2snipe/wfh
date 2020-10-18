package wfh.status.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import wfh.status.RunNotOnEDTStatusChangeListener;
import wfh.status.Status;

@Component
public class LoggingStatusChangeListener extends RunNotOnEDTStatusChangeListener {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void performStatusChange(Status previousStatus, Status newStatus) {
        logger.info("Status change from {} to {}", previousStatus, newStatus);
    }
}
