package wfh.gui.theme;

import wfh.settings.SettingsRepository;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeThemeAction implements ActionListener {
    private final boolean useDarkTheme;
    private final SettingsRepository settingsRepository;
    private final LookAndFeelApplier lookAndFeelApplier = new LookAndFeelApplier();

    public ChangeThemeAction(boolean useDarkTheme, SettingsRepository settingsRepository) {
        this.useDarkTheme = useDarkTheme;
        this.settingsRepository = settingsRepository;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        settingsRepository.saveToUseDarkTheme(useDarkTheme);
        lookAndFeelApplier.apply(settingsRepository.findLookAndFeel());
    }
}
