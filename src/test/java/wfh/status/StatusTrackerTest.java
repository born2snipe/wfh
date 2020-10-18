package wfh.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static wfh.status.Status.AFK;
import static wfh.status.Status.DONE_FOR_THE_DAY;

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
    public void changeStatusTo_does_NOT_notifies_listener_when_the_old_and_new_status_are_the_same() {
        tracker.changeStatusTo(AFK);
        tracker.changeStatusTo(AFK);

        verify(listener).statusChanged(DONE_FOR_THE_DAY, AFK);
        verifyNoMoreInteractions(listener);
    }

    @Test
    public void changeStatusTo_notifies_listener() {
        tracker.changeStatusTo(AFK);

        verify(listener).statusChanged(DONE_FOR_THE_DAY, AFK);
    }

    @Test
    public void changeStatusTo_to_a_new_status() {
        tracker.changeStatusTo(AFK);

        assertEquals(AFK, tracker.getCurrentStatus());
    }

    @Test
    public void init_status_provided() {
        when(currentStatusAccessor.findCurrentStatus()).thenReturn(Optional.of(AFK));

        tracker.init();

        assertEquals(AFK, tracker.getCurrentStatus());
    }

    @Test
    public void init_did_not_determine_status() {
        tracker.init();

        assertEquals(DONE_FOR_THE_DAY, tracker.getCurrentStatus());
    }
}