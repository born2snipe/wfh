package wfh.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static wfh.status.Statuses.DONE_FOR_THE_DAY;

@ExtendWith(MockitoExtension.class)
class StatusTrackerTest {
    private StatusTracker tracker;
    @Mock
    private CurrentStatusAccessor currentStatusAccessor;
    @Mock
    private StatusChangedListener listener;

    @BeforeEach
    void setUp() {
        tracker = new StatusTracker(currentStatusAccessor, Arrays.asList(listener));
        tracker.init();
    }

    @Test
    public void changeStatusTo_notifies_listener() {
        tracker.changeStatusTo("NEW_STATUS");

        verify(listener).statusChanged(DONE_FOR_THE_DAY.name(), "NEW_STATUS");
    }

    @Test
    public void changeStatusTo_to_a_new_status() {
        tracker.changeStatusTo("NEW_STATUS");

        assertEquals("NEW_STATUS", tracker.getCurrentStatus());
    }

    @Test
    public void init_status_provided() {
        when(currentStatusAccessor.findCurrentStatus()).thenReturn(Optional.of("status"));

        tracker.init();

        assertEquals("status", tracker.getCurrentStatus());
    }

    @Test
    public void init_did_not_determine_status() {
        tracker.init();

        assertEquals(DONE_FOR_THE_DAY.name(), tracker.getCurrentStatus());
    }
}