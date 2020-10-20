package wfh.status.time;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class WorkDayServiceTest {
    @InjectMocks
    private WorkDayService service;
    @Mock
    private WorkDayRepository repository;

    @Test
    public void findSundayFrom() {
        LocalDate date = LocalDate.of(2020, 10, 20);

        LocalDate sunday = service.findSundayFrom(date);

        assertEquals(date.minusDays(2), sunday);
    }
}