package wfh.status.time;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static wfh.status.Status.*;

@Service
public class WorkDayService {
    private final WorkDayRepository repository;

    public WorkDayService(WorkDayRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public TodayElapsedTimes findElapsedTimesForToday() {
        return findTimesFor(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public Map<Day, TodayElapsedTimes> findElapsedTimesForWeekOf(LocalDate sunday) {
        HashMap<Day, TodayElapsedTimes> dayToTimes = new HashMap<>();
        for (int i = 0; i < 7; i++) {
            LocalDate date = sunday.plusDays(i);
            dayToTimes.put(Day.from(date.getDayOfWeek()), findTimesFor(date));
        }
        return dayToTimes;
    }

    private TodayElapsedTimes findTimesFor(LocalDate date) {
        return repository.findByDay(date)
                .map((wd) -> new TodayElapsedTimes(
                        wd.calculateTime(AFK),
                        wd.calculateTime(LUNCH),
                        wd.calculateTime(WORKING)
                ))
                .orElse(new TodayElapsedTimes(0, 0, 0));
    }

    public LocalDate findSundayFrom(LocalDate date) {
        Day today = Day.from(date.getDayOfWeek());
        int daysToSubtract = today.ordinal();
        return date.minusDays(daysToSubtract);
    }
}
