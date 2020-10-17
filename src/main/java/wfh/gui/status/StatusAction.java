package wfh.gui.status;

import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.ActionEvent;
import java.util.Optional;

import static javax.swing.KeyStroke.getKeyStroke;

public class StatusAction extends AbstractAction {
    private StatusActionTask task;

    public StatusAction(String name, Optional<String> hotKey, StatusActionTask task) {
        this.task = task;
        putValue(Action.NAME, name);
        hotKey.ifPresent((hk) -> putValue(Action.ACCELERATOR_KEY, getKeyStroke(hk)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        task.execute();
    }
}
