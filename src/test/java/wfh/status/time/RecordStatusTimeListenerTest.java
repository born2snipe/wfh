package wfh.status.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wfh.status.Status.*;


@ExtendWith(MockitoExtension.class)
class RecordStatusTimeListenerTest {
    @InjectMocks
    private RecordStatusTimeListener listener;
    @Mock
    private WorkDayRepository repository;
    @Captor
    private ArgumentCaptor<WorkDay> workDayCaptor;
    private LocalDate now;

    @BeforeEach
    void setUp() {
        now = LocalDate.now();
    }

    @Test
    public void status_change_to_DONE_FOR_THE_DAY() {
        StatusChange statusChange = new StatusChange();
        statusChange.setStatus(AFK);

        WorkDay existingWorkDay = new WorkDay();
        existingWorkDay.getStatusChanges().add(statusChange);

        when(repository.findByDay(now)).thenReturn(Optional.of(existingWorkDay));

        listener.statusChanged(AFK, DONE_FOR_THE_DAY);

        verify(repository).save(workDayCaptor.capture());

        WorkDay workDay = workDayCaptor.getValue();
        assertThat(workDay.getStatusChanges().size(), is(1));
    }

    @Test
    public void status_change_added_after_existing_finished() {
        StatusChange statusChange = new StatusChange();
        statusChange.setStatus(AFK);

        WorkDay existingWorkDay = new WorkDay();
        existingWorkDay.getStatusChanges().add(statusChange);

        when(repository.findByDay(now)).thenReturn(Optional.of(existingWorkDay));

        listener.statusChanged(AFK, WORKING);

        verify(repository).save(workDayCaptor.capture());

        WorkDay workDay = workDayCaptor.getValue();
        assertThat(workDay.getStatusChanges().size(), is(2));
        assertThat(workDay.getStatusChanges().get(1).getStatus(), is(WORKING));
        assertThat(workDay.getStatusChanges().get(1).getStart(), notNullValue());
        assertThat(workDay.getStatusChanges().get(1).getStop(), nullValue());
    }

    @Test
    public void finish_existing_status_change() {
        StatusChange statusChange = new StatusChange();
        statusChange.setStatus(AFK);

        WorkDay existingWorkDay = new WorkDay();
        existingWorkDay.getStatusChanges().add(statusChange);

        when(repository.findByDay(now)).thenReturn(Optional.of(existingWorkDay));

        listener.statusChanged(AFK, WORKING);

        verify(repository).save(workDayCaptor.capture());

        WorkDay workDay = workDayCaptor.getValue();
        assertThat(workDay.getStatusChanges().get(0), is(statusChange));
        assertThat(workDay.getStatusChanges().get(0).getStop(), notNullValue());
    }

    @Test
    public void start_of_the_day_add_status_change() {
        when(repository.findByDay(now)).thenReturn(Optional.empty());

        listener.statusChanged(DONE_FOR_THE_DAY, AFK);

        verify(repository).save(workDayCaptor.capture());

        WorkDay workDay = workDayCaptor.getValue();
        assertThat(workDay.getStatusChanges().size(), is(1));
        assertThat(workDay.getStatusChanges().get(0).getStatus(), is(AFK));
        assertThat(workDay.getStatusChanges().get(0).getStart(), notNullValue());
        assertThat(workDay.getStatusChanges().get(0).getStop(), is(nullValue()));
    }

    @Test
    public void start_of_the_day() {
        when(repository.findByDay(now)).thenReturn(Optional.empty());

        listener.statusChanged(DONE_FOR_THE_DAY, AFK);

        verify(repository).save(workDayCaptor.capture());

        WorkDay workDay = workDayCaptor.getValue();
        assertThat(workDay.getDay(), is(now));
    }
}