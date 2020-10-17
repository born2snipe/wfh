package wfh.settings;

import com.formdev.flatlaf.FlatDarculaLaf;
import org.springframework.stereotype.Repository;

@Repository
public class NoOpSettingsRepository implements SettingsRepository {
    @Override
    public void saveLookAndFeel(Class lookAndFeelClass) {
        System.out.println("lookAndFeelClass = " + lookAndFeelClass);
    }

    @Override
    public Class findLookAndFeel() {
        return FlatDarculaLaf.class;
    }

    @Override
    public String getLocale() {
        return "en";
    }

    @Override
    public void setLocale(String locale) {

    }
}
