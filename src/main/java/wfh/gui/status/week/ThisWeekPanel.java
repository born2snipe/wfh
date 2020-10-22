package wfh.gui.status.week;

import com.jgoodies.forms.builder.FormBuilder;
import wfh.status.time.Day;
import wfh.status.time.TodayElapsedTimes;
import wfh.status.time.WorkDayService;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.time.LocalDate;
import java.util.Map;

public class ThisWeekPanel extends JPanel {
    private final WorkDayService workDayService;
    private WeekModel model;

    public ThisWeekPanel(WorkDayService workDayService) {
        this.workDayService = workDayService;

        model = new WeekModel();
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setPreferredSize(new Dimension(0, 200));

        JPanel panel = FormBuilder.create()
                .columns("5dlu, pref:grow, 5dlu")
                .rows("5dlu, pref, 5dlu")
                .add(scrollPane).xy(2, 2)
                .build();

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

    }

    public void updateElapsedTimes() {
        LocalDate sunday = workDayService.findSundayFrom(LocalDate.now());
        Map<Day, TodayElapsedTimes> elapsedTimesForWeek = workDayService.findElapsedTimesForWeekOf(sunday);

        model.setElapsedTimes(elapsedTimesForWeek);
    }
}
