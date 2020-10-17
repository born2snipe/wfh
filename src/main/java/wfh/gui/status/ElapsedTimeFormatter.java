package wfh.gui.status;

import java.text.DecimalFormat;
import java.time.Duration;

public class ElapsedTimeFormatter {
    private final static long millisInAHour = Duration.ofHours(1).toMillis();
    private final static long millisInAMinute = Duration.ofMinutes(1).toMillis();
    private final static long millisInASecond = Duration.ofSeconds(1).toMillis();

    public static String format(long elapsedTimeInMillis) {
        long hours = elapsedTimeInMillis / millisInAHour;
        long remaining = elapsedTimeInMillis % millisInAHour;
        long minutes = remaining / millisInAMinute;
        remaining = elapsedTimeInMillis % millisInAMinute;
        long seconds = remaining / millisInASecond;

        DecimalFormat format = new DecimalFormat("##");
        format.setMinimumIntegerDigits(2);

        StringBuilder builder = new StringBuilder();
        builder.append(format.format(hours)).append(":");
        builder.append(format.format(minutes)).append(":");
        builder.append(format.format(seconds));
        return builder.toString();
    }
}
