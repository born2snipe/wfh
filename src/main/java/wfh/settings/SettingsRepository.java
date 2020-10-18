package wfh.settings;

import wfh.status.Status;

import java.awt.Color;
import java.util.Optional;

public interface SettingsRepository {
    Class findLookAndFeel();

    Optional<String> findHotKeyFor(Status cannedAction);

    Color findStatusColorFor(Status action);

    void saveToUseDarkTheme(boolean useDarkTheme);

    boolean useDarkTheme();
}
