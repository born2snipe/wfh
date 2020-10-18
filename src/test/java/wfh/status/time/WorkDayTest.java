package wfh.status.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static wfh.status.Status.AFK;

class WorkDayTest {
    private WorkDay workDay;

    @BeforeEach
    void setUp() {
        workDay = new WorkDay();
    }

    @Test
    public void calculateTime_with_an_unfinished_status_changes() {
        StatusChange statusChange = new StatusChange();
        statusChange.setStatus(AFK);
        statusChange.setStop(LocalDateTime.now().plusHours(2));

        StatusChange otherStatusChange = new StatusChange();
        otherStatusChange.setStatus(AFK);

        workDay.getStatusChanges().add(statusChange);
        workDay.getStatusChanges().add(otherStatusChange);

        assertThat(workDay.calculateTime(AFK), is(statusChange.getElapsedTime() + otherStatusChange.getElapsedTime()));
    }

    @Test
    public void calculateTime_all_finished_status_changes() {
        StatusChange statusChange = new StatusChange();
        statusChange.setStatus(AFK);
        statusChange.setStop(LocalDateTime.now().plusHours(2));

        StatusChange otherStatusChange = new StatusChange();
        otherStatusChange.setStatus(AFK);
        otherStatusChange.setStop(LocalDateTime.now().plusHours(2));

        workDay.getStatusChanges().add(statusChange);
        workDay.getStatusChanges().add(otherStatusChange);

        assertThat(workDay.calculateTime(AFK), is(statusChange.getElapsedTime() + otherStatusChange.getElapsedTime()));
    }
}