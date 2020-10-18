package wfh.status.time;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wfh.status.Status;
import wfh.status.StatusChangedListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class RecordStatusTimeListener implements StatusChangedListener {
    private final WorkDayRepository repository;

    public RecordStatusTimeListener(WorkDayRepository repository) {
        this.repository = repository;
    }

    @Transactional
    @Override
    public void statusChanged(Status previousStatus, Status newStatus) {
        WorkDay workDay = repository.findByDay(LocalDate.now())
                .orElse(newWorkDay());

        workDay.getMostRecentUnfinishedStatusChange()
                .ifPresent((sc) -> sc.setStop(LocalDateTime.now()));

        if (newStatus != Status.DONE_FOR_THE_DAY) {
            StatusChange statusChange = new StatusChange();
            statusChange.setStatus(newStatus);
            workDay.getStatusChanges().add(statusChange);
        }

        repository.save(workDay);
    }

    private WorkDay newWorkDay() {
        WorkDay workDay = new WorkDay();
        workDay.setDay(LocalDate.now());
        return workDay;
    }
}
