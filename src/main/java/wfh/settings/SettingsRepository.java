package wfh.settings;

public interface SettingsRepository {
    void saveLookAndFeel(Class lookAndFeelClass);

    Class findLookAndFeel();

    String getLocale();

    void setLocale(String locale);
}
