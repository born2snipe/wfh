package wfh.status.time;

import java.time.DayOfWeek;

public enum Day {
    SUNDAY(DayOfWeek.SUNDAY, "SUN"),
    MONDAY(DayOfWeek.MONDAY, "MON"),
    TUESDAY(DayOfWeek.TUESDAY, "TUE"),
    WEDNESDAY(DayOfWeek.WEDNESDAY, "WED"),
    THURSDAY(DayOfWeek.THURSDAY, "THU"),
    FRIDAY(DayOfWeek.FRIDAY, "FRI"),
    SATURDAY(DayOfWeek.SATURDAY, "SAT");

    private final DayOfWeek dayOfWeek;
    private final String shortName;


    Day(DayOfWeek dayOfWeek, String shortName) {
        this.dayOfWeek = dayOfWeek;
        this.shortName = shortName;
    }

    public static Day from(DayOfWeek dayOfWeek) {
        for (Day value : values()) {
            if (value.dayOfWeek == dayOfWeek) {
                return value;
            }
        }

        return null;
    }

    public String getShortName() {
        return shortName;
    }
}
