package wfh.status.time;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class WorkDayServiceTest {
    @InjectMocks
    private WorkDayService service;
    @Mock
    private WorkDayRepository repository;


    public static Stream<LocalDate> oneWholeWeek() {
        return Arrays.stream(new LocalDate[]{
                LocalDate.of(2020, 10, 18),
                LocalDate.of(2020, 10, 19),
                LocalDate.of(2020, 10, 20),
                LocalDate.of(2020, 10, 21),
                LocalDate.of(2020, 10, 22),
                LocalDate.of(2020, 10, 23),
                LocalDate.of(2020, 10, 24)
        });
    }

    @ParameterizedTest
    @MethodSource("oneWholeWeek")
    public void findSundayFrom(LocalDate date) {
        LocalDate sunday = service.findSundayFrom(date);

        assertEquals(LocalDate.of(2020, 10, 18), sunday);
    }
}