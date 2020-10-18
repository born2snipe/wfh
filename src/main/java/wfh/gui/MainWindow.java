package wfh.gui;

import com.jgoodies.forms.builder.FormBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wfh.gui.status.StatusTimerPanel;
import wfh.settings.SettingsRepository;
import wfh.status.time.WorkDayRepository;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.time.LocalDate;
import java.util.List;

import static wfh.status.Status.*;

@Component
public class MainWindow extends JFrame {
    private final StatusTimerPanel afk;
    private final StatusTimerPanel lunch;
    private final StatusTimerPanel work;
    private final WorkDayRepository workDayRepository;

    public MainWindow(@Value("${app.title}") String title, List<JMenu> menus, SettingsRepository settingsRepository, WorkDayRepository workDayRepository) throws HeadlessException {
        super(title);
        this.workDayRepository = workDayRepository;

        JMenuBar menuBar = new JMenuBar();
        menus.forEach(menuBar::add);
        setJMenuBar(menuBar);

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

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(panel, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationByPlatform(true);
    }

    @Transactional
    @Scheduled(fixedDelay = 100L)
    public void updateElapsedTimes() {
        workDayRepository.findByDay(LocalDate.now()).ifPresent((wd) -> {
            afk.setElapsedTime(wd.calculateTime(AFK));
            lunch.setElapsedTime(wd.calculateTime(LUNCH));
            work.setElapsedTime(wd.calculateTime(WORKING));
        });
    }
}
