package wfh.gui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import wfh.gui.status.today.TodayPanel;
import wfh.settings.SettingsRepository;
import wfh.status.time.WorkDayRepository;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTabbedPane;
import java.awt.BorderLayout;
import java.util.List;

@Component
public class MainWindow extends JFrame {

    private TodayPanel todayPanel;

    @Autowired
    public MainWindow(
            @Value("${app.title}") String title,
            List<JMenu> menus,
            SettingsRepository settingsRepository,
            WorkDayRepository workDayRepository
    ) {
        super(title);

        JMenuBar menuBar = new JMenuBar();
        menus.forEach(menuBar::add);
        setJMenuBar(menuBar);

        todayPanel = new TodayPanel(settingsRepository, workDayRepository);
        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Today", todayPanel);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(tabs, BorderLayout.CENTER);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setResizable(false);
        setLocationByPlatform(true);
    }

    @Transactional(readOnly = true)
    @Scheduled(fixedRate = 10L)
    public void updateElapsedTimes() {
        todayPanel.updateElapsedTimes();
    }
}
