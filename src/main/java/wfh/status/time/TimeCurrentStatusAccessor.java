package wfh.status.time;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wfh.status.CurrentStatusAccessor;
import wfh.status.Status;

import java.time.LocalDate;
import java.util.Optional;

@Component
public class TimeCurrentStatusAccessor implements CurrentStatusAccessor {
    private final WorkDayRepository repository;

    public TimeCurrentStatusAccessor(WorkDayRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    @Override
    public Optional<Status> findCurrentStatus() {
        return repository.findByDay(LocalDate.now())
                .flatMap(WorkDay::getMostRecentUnfinishedStatusChange)
                .map(StatusChange::getStatus);
    }
}
