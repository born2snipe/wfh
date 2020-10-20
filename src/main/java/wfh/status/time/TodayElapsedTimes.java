package wfh.status.time;

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
}
