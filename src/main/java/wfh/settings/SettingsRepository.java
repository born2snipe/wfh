package wfh.settings;

import wfh.gui.status.CannedActions;

import java.util.Optional;

public interface SettingsRepository {
    void saveLookAndFeel(Class lookAndFeelClass);

    Class findLookAndFeel();

    String getLocale();

    void setLocale(String locale);

    Optional<String> findHotKeyFor(CannedActions cannedAction);
}
