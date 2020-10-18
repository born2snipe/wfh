package wfh.status.time;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import wfh.status.Status;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TimeCurrentStatusAccessorTest {
    @InjectMocks
    private TimeCurrentStatusAccessor accessor;
    @Mock
    private WorkDayRepository repository;

    @Test
    public void work_day_found_with_unfinished_status_change() {
        StatusChange statusChange = new StatusChange();
        statusChange.setStatus(Status.AFK);

        WorkDay workDay = new WorkDay();
        workDay.getStatusChanges().add(statusChange);

        when(repository.findByDay(LocalDate.now())).thenReturn(Optional.of(workDay));

        Optional<Status> status = accessor.findCurrentStatus();

        assertThat(status, is(Optional.of(Status.AFK)));
    }

    @Test
    public void work_day_found_but_no_status_changes() {
        when(repository.findByDay(LocalDate.now())).thenReturn(Optional.of(new WorkDay()));

        Optional<Status> status = accessor.findCurrentStatus();

        assertThat(status, is(Optional.empty()));
    }

    @Test
    public void no_work_day_found() {
        when(repository.findByDay(LocalDate.now())).thenReturn(Optional.empty());

        Optional<Status> status = accessor.findCurrentStatus();

        assertThat(status, is(Optional.empty()));
    }
}