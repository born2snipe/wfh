package wfh.gui;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

import static wfh.WorkingFromHomeApp.WINDOW;

public class LookAndFeelApplier {
    public void apply(Class lookAndFeelClass) {
        try {
            UIManager.setLookAndFeel(lookAndFeelClass.getName());
            SwingUtilities.updateComponentTreeUI(WINDOW);
        } catch (Exception exception) {

        }
    }
}
