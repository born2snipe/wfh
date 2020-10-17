package wfh.status.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import wfh.status.RunNotOnEDTStatusChangeListener;

@Component
public class LoggingStatusChangeListener extends RunNotOnEDTStatusChangeListener {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void performStatusChange(String previousStatus, String newStatus) {
        logger.info("Status change from {} to {}", previousStatus, newStatus);
    }
}
