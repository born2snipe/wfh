package wfh.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wfh.gui.status.today.TodayPanel;
import wfh.gui.status.week.ThisWeekPanel;
import wfh.settings.SettingsRepository;
import wfh.status.CurrentStatusAccessor;
import wfh.status.Status;
import wfh.status.time.WorkDayService;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.util.List;

import static wfh.status.Status.DONE_FOR_THE_DAY;

@Component
public class MainWindow extends JFrame {
    private final TodayPanel todayPanel;
    private final ThisWeekPanel thisWeekPanel;
    private final String title;
    private final CurrentStatusAccessor currentStatusAccessor;

    @Autowired
    public MainWindow(
            @Value("${app.title}") String title,
            List<JMenu> menus,
            SettingsRepository settingsRepository,
            WorkDayService workDayService,
            CurrentStatusAccessor currentStatusAccessor
    ) {
        super(title);
        this.title = title;
        this.currentStatusAccessor = currentStatusAccessor;

        JMenuBar menuBar = new JMenuBar();
        menus.forEach(menuBar::add);
        setJMenuBar(menuBar);

        todayPanel = new TodayPanel(settingsRepository, workDayService);
        thisWeekPanel = new ThisWeekPanel(workDayService);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Today", todayPanel);
        tabs.addTab("Week", thisWeekPanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tabs, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationByPlatform(true);
    }

    @Transactional(readOnly = true)
    public void updateElapsedTimes() {
        todayPanel.updateElapsedTimes();
        thisWeekPanel.updateElapsedTimes();

        Status newStatus = currentStatusAccessor.findCurrentStatus().orElse(DONE_FOR_THE_DAY);
        if (newStatus == DONE_FOR_THE_DAY) {
            setTitle(title);
        } else {
            setTitle(title + " - " + newStatus);
        }
    }
}
