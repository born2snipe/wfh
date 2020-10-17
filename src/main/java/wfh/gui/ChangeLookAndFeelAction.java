package wfh.gui;

import wfh.settings.SettingsRepository;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChangeLookAndFeelAction implements ActionListener {
    private final Class lookAndFeelClass;
    private SettingsRepository settingsRepository;
    private LookAndFeelApplier lookAndFeelApplier = new LookAndFeelApplier();

    public ChangeLookAndFeelAction(Class lookAndFeelClass, SettingsRepository settingsRepository) {
        this.lookAndFeelClass = lookAndFeelClass;
        this.settingsRepository = settingsRepository;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        lookAndFeelApplier.apply(lookAndFeelClass);
        settingsRepository.saveLookAndFeel(lookAndFeelClass);
    }
}
