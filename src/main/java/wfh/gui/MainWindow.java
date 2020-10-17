package wfh.gui;

import com.jgoodies.forms.builder.FormBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import wfh.gui.status.StatusTimerPanel;
import wfh.status.CannedActions;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.HeadlessException;
import java.util.List;

@Component
public class MainWindow extends JFrame {
    private final StatusTimerPanel afk;
    private final StatusTimerPanel lunch;
    private final StatusTimerPanel work;

    private final long start = System.currentTimeMillis();

    public MainWindow(@Value("${app.title}") String title, List<JMenu> menus) throws HeadlessException {
        super(title);

        JMenuBar menuBar = new JMenuBar();
        menus.forEach(menuBar::add);
        setJMenuBar(menuBar);

        afk = new StatusTimerPanel(CannedActions.AFK, 32f, Color.RED);
        lunch = new StatusTimerPanel(CannedActions.LUNCH, 32f, Color.RED);
        work = new StatusTimerPanel(CannedActions.BACK_TO_WORK, 64f, Color.GREEN.darker());

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
        setSize(300, 230);
        setResizable(false);
        setLocationByPlatform(true);
    }

    @Scheduled(fixedDelay = 100L)
    public void updateElapsedTimes() {
        // todo - query for the real elapse times from the tracker

        long now = System.currentTimeMillis();

        afk.setElapsedTime(now - start);
        lunch.setElapsedTime(now - start);
        work.setElapsedTime(now - start);
    }
}
