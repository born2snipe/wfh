package wfh.settings;

import wfh.status.Statuses;

import java.awt.Color;
import java.util.Optional;

public interface SettingsRepository {
    Class findLookAndFeel();

    Optional<String> findHotKeyFor(Statuses cannedAction);

    Color findStatusColorFor(Statuses action);

    void saveToUseDarkTheme(boolean useDarkTheme);

    boolean useDarkTheme();
}
