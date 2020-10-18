package wfh.status.time;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import wfh.status.Status;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.Optional.empty;
import static java.util.Optional.of;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@DataJpaTest(showSql = false)
class WorkDayRepositoryTest {
    @Autowired
    private WorkDayRepository repo;
    private LocalDate date;
    private StatusChange statusChange;

    @BeforeEach
    void setUp() {
        date = LocalDate.now();

        statusChange = new StatusChange();
        statusChange.setStop(LocalDateTime.now().plusHours(2));
        statusChange.setStatus(Status.AFK);

    }

    @Test
    public void status_changes_require_a_start_date() {
        statusChange.setStart(null);

        WorkDay workDay = new WorkDay();
        workDay.setDay(date);
        workDay.getStatusChanges().add(statusChange);

        assertThrows(DataIntegrityViolationException.class, () -> repo.save(workDay));
    }

    @Test
    public void status_changes_require_a_status() {
        statusChange.setStatus(null);

        WorkDay workDay = new WorkDay();
        workDay.setDay(date);
        workDay.getStatusChanges().add(statusChange);

        assertThrows(DataIntegrityViolationException.class, () -> repo.save(workDay));
    }

    @Test
    public void status_changes_are_available() {
        WorkDay workDay = new WorkDay();
        workDay.setDay(date);
        workDay.getStatusChanges().add(statusChange);

        repo.save(workDay);

        List<StatusChange> statusChanges = repo.findByDay(date).get().getStatusChanges();
        assertEquals(1, statusChanges.size());
        assertEquals(statusChange.getStart(), statusChanges.get(0).getStart());
        assertEquals(statusChange.getStop(), statusChanges.get(0).getStop());
        assertEquals(statusChange.getStatus(), statusChanges.get(0).getStatus());
    }

    @Test
    public void id_is_generated() {
        WorkDay workDay = new WorkDay();
        workDay.setDay(date);

        workDay = repo.save(workDay);

        assertNotNull(workDay.getId());
    }

    @Test
    public void look_up_by_date_and_nothing_found() {
        assertEquals(empty(), repo.findByDay(date));
    }

    @Test
    public void look_up_by_date() {
        WorkDay workDay = new WorkDay();
        workDay.setDay(date);

        workDay = repo.save(workDay);

        assertEquals(of(workDay), repo.findByDay(date));
    }

    @Test
    public void no_duplicate_work_days() {
        WorkDay workDay = new WorkDay();
        workDay.setDay(date);

        WorkDay otherWorkDay = new WorkDay();
        otherWorkDay.setDay(date);

        repo.save(workDay);

        assertThrows(DataIntegrityViolationException.class, () -> repo.save(otherWorkDay));
    }
}