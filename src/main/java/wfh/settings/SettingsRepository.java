package wfh.settings;

import wfh.status.CannedActions;

import java.awt.Color;
import java.util.Optional;

public interface SettingsRepository {
    void saveLookAndFeel(Class lookAndFeelClass);

    Class findLookAndFeel();

    Optional<String> findHotKeyFor(CannedActions cannedAction);

    Color findStatusColorFor(CannedActions action);
}
