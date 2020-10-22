package wfh.status.time;

import java.util.Objects;

public class TodayElapsedTimes {
    private final long afk;
    private final long lunch;
    private final long work;

    public TodayElapsedTimes(long afk, long lunch, long work) {
        this.afk = afk;
        this.lunch = lunch;
        this.work = work;
    }

    public long getAfk() {
        return afk;
    }

    public long getLunch() {
        return lunch;
    }

    public long getWork() {
        return work;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TodayElapsedTimes that = (TodayElapsedTimes) o;
        return afk == that.afk &&
                lunch == that.lunch &&
                work == that.work;
    }

    @Override
    public int hashCode() {
        return Objects.hash(afk, lunch, work);
    }
}
