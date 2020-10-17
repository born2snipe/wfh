package wfh.gui.status;

import wfh.status.StatusTracker;

import javax.swing.AbstractAction;
import javax.swing.Action;
import java.awt.event.ActionEvent;
import java.util.Optional;

import static javax.swing.KeyStroke.getKeyStroke;

public class StatusAction extends AbstractAction {
    private String name;
    private StatusTracker statusTracker;

    public StatusAction(String name, String menuText, Optional<String> hotKey, StatusTracker statusTracker) {
        this.name = name;
        this.statusTracker = statusTracker;
        putValue(Action.NAME, menuText);
        hotKey.ifPresent((hk) -> putValue(Action.ACCELERATOR_KEY, getKeyStroke(hk)));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        statusTracker.changeStatusTo(name);
    }

    public String getName() {
        return name;
    }
}
