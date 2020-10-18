package wfh.status.window;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import wfh.WorkingFromHomeApp;
import wfh.gui.MainWindow;
import wfh.status.StatusChangedListener;

@Component
public class UpdateWindowTitleStatusChangeListener implements StatusChangedListener {
    private final String title;

    public UpdateWindowTitleStatusChangeListener(@Value("${app.title}") String title) {
        this.title = title;
    }

    @Override
    public void statusChanged(String previousStatus, String newStatus) {
        MainWindow window = WorkingFromHomeApp.WINDOW;
        window.setTitle(title + " - " + newStatus);
    }
}
