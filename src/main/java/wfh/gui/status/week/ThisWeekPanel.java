package wfh.gui.status.week;

import wfh.status.time.Day;
import wfh.status.time.TodayElapsedTimes;
import wfh.status.time.WorkDayService;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import java.awt.BorderLayout;
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

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);

    }

    public void updateElapsedTimes() {
        LocalDate sunday = workDayService.findSundayFrom(LocalDate.now());
        Map<Day, TodayElapsedTimes> elapsedTimesForWeek = workDayService.findElapsedTimesForWeekOf(sunday);

        model.setElapsedTimes(elapsedTimesForWeek);
    }
}
