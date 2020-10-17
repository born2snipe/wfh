package wfh.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wfh.gui.lnf.LookAndFeelApplier;
import wfh.settings.SettingsRepository;

@Component
public class PreGuiLaunchHook {
    @Autowired
    private SettingsRepository settingsRepository;
    private LookAndFeelApplier lookAndFeelApplier = new LookAndFeelApplier();

    public void execute(MainWindow window) {
        lookAndFeelApplier.apply(settingsRepository.findLookAndFeel());
    }
}
