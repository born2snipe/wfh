package wfh.status;

import java.util.Optional;

public interface CurrentStatusAccessor {
    Optional<Status> findCurrentStatus();
}
