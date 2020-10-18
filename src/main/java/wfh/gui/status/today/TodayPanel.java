package wfh.gui.status.today;

import com.jgoodies.forms.builder.FormBuilder;
import wfh.gui.status.StatusTimerPanel;
import wfh.settings.SettingsRepository;
import wfh.status.time.WorkDayRepository;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.time.LocalDate;

import static wfh.status.Status.*;

public class TodayPanel extends JPanel {
    private final StatusTimerPanel afk;
    private final StatusTimerPanel lunch;
    private final StatusTimerPanel work;
    private final WorkDayRepository workDayRepository;

    public TodayPanel(SettingsRepository settingsRepository, WorkDayRepository workDayRepository) {
        this.workDayRepository = workDayRepository;

        Color afkColor = settingsRepository.findStatusColorFor(AFK);
        Color lunchColor = settingsRepository.findStatusColorFor(LUNCH);
        Color workColor = settingsRepository.findStatusColorFor(WORKING);

        afk = new StatusTimerPanel(AFK, 32f, afkColor);
        lunch = new StatusTimerPanel(LUNCH, 32f, lunchColor);
        work = new StatusTimerPanel(WORKING, 64f, workColor);

        JPanel panel = FormBuilder.create()
                .columns("p:grow, 5dlu, p:grow")
                .rows("p:grow, 5dlu, p:grow")
                .add(afk).xy(1, 1)
                .add(lunch).xy(3, 1)
                .add(work).xyw(1, 3, 3)
                .build();

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);
    }

    public void updateElapsedTimes() {
        // todo - need to reset back to zero if not present
        workDayRepository.findByDay(LocalDate.now()).ifPresent((wd) -> {
            afk.setElapsedTime(wd.calculateTime(AFK));
            lunch.setElapsedTime(wd.calculateTime(LUNCH));
            work.setElapsedTime(wd.calculateTime(WORKING));
        });
    }
}
