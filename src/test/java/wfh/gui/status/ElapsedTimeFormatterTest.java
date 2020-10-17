package wfh.gui.status;

import org.junit.jupiter.api.Test;

import static java.time.Duration.*;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static wfh.gui.status.ElapsedTimeFormatter.format;

class ElapsedTimeFormatterTest {

    @Test
    public void format_hours() {
        assertThat(format(ofHours(15).toMillis()), is("15:00:00"));
    }

    @Test
    public void format_minutes() {
        assertThat(format(ofMinutes(20).toMillis()), is("00:20:00"));
    }

    @Test
    public void format_seconds() {
        assertThat(format(ofSeconds(10).toMillis()), is("00:00:10"));
    }
}