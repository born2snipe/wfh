package wfh.flowdock;

import org.springframework.stereotype.Component;
import wfh.status.CurrentStatusAccessor;

import java.util.Optional;

@Component
public class FlowDockCurrentStatusAccessor implements CurrentStatusAccessor {
    @Override
    public Optional<String> findCurrentStatus() {
        return Optional.empty();
    }
}
