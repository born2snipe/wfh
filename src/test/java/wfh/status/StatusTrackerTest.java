package wfh.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static wfh.status.CannedActions.DONE_FOR_THE_DAY;

@ExtendWith(MockitoExtension.class)
class StatusTrackerTest {
    @InjectMocks
    private StatusTracker tracker;
    @Mock
    private CurrentStatusAccessor currentStatusAccessor;

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