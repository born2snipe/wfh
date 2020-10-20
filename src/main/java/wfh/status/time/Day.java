package wfh.status.time;

import java.time.DayOfWeek;

public enum Day {
    SUNDAY(DayOfWeek.SUNDAY),
    MONDAY(DayOfWeek.MONDAY),
    TUESDAY(DayOfWeek.TUESDAY),
    WEDNESDAY(DayOfWeek.WEDNESDAY),
    THURSDAY(DayOfWeek.THURSDAY),
    FRIDAY(DayOfWeek.FRIDAY),
    SATURDAY(DayOfWeek.SATURDAY);

    private final DayOfWeek dayOfWeek;

    Day(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public static Day from(DayOfWeek dayOfWeek) {
        for (Day value : values()) {
            if (value.dayOfWeek == dayOfWeek) {
                return value;
            }
        }

        return null;
    }
}
