package wfh.flowdock;

import org.springframework.stereotype.Component;
import wfh.status.CurrentStatusAccessor;
import wfh.status.Status;

import java.util.Optional;

@Component
public class FlowDockCurrentStatusAccessor implements CurrentStatusAccessor {
    @Override
    public Optional<Status> findCurrentStatus() {
        return Optional.empty();
    }
}
