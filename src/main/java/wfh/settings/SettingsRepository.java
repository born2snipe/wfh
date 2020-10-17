package wfh.settings;

import wfh.status.CannedActions;

import java.awt.Color;
import java.util.Optional;

public interface SettingsRepository {
    Class findLookAndFeel();

    Optional<String> findHotKeyFor(CannedActions cannedAction);

    Color findStatusColorFor(CannedActions action);

    void saveToUseDarkTheme(boolean useDarkTheme);

    boolean useDarkTheme();
}
