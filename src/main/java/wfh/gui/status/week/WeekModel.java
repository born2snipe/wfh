package wfh.gui.status.week;

import wfh.status.time.Day;
import wfh.status.time.TodayElapsedTimes;

import javax.swing.table.AbstractTableModel;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static wfh.gui.status.ElapsedTimeFormatter.format;

public class WeekModel extends AbstractTableModel {
    private static final String[] COLUMNS = new String[]{
            "Date", "AFK", "Lunch", "Work"
    };

    private final Map<Day, TodayElapsedTimes> dayToTimes = Collections.synchronizedMap(new HashMap<>());

    @Override
    public String getColumnName(int column) {
        return COLUMNS[column];
    }

    @Override
    public int getRowCount() {
        return Day.values().length;
    }

    @Override
    public int getColumnCount() {
        return COLUMNS.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Day dayOfWeek = Day.values()[rowIndex];
        TodayElapsedTimes times = dayToTimes.getOrDefault(dayOfWeek, new TodayElapsedTimes(0, 0, 0));

        switch (columnIndex) {
            case 0:
                return dayOfWeek.getShortName();
            case 1:
                return format(times.getAfk());
            case 2:
                return format(times.getLunch());
            case 3:
                return format(times.getWork());
            default:
                return null;
        }
    }

    public void setElapsedTimes(Map<Day, TodayElapsedTimes> updatedTimes) {
        int lowestRowUpdated = -1;
        int highestRowUpdated = -1;

        synchronized (dayToTimes) {
            for (int i = 0; i < Day.values().length; i++) {
                Day day = Day.values()[i];

                TodayElapsedTimes updated = updatedTimes.get(day);
                TodayElapsedTimes existing = dayToTimes.get(day);
                if (!updated.equals(existing)) {
                    dayToTimes.put(day, updated);

                    if (lowestRowUpdated == -1) {
                        lowestRowUpdated = i;
                        highestRowUpdated = i;
                    } else if (highestRowUpdated < i) {
                        highestRowUpdated = i;
                    }
                }
            }
        }

        if (lowestRowUpdated >= 0) {
            fireTableRowsUpdated(lowestRowUpdated, highestRowUpdated);
        }
    }
}
