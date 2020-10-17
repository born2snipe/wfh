package wfh.gui.status;

import wfh.status.CannedActions;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class StatusTimerPanel extends JPanel {
    private static Font clockFont;

    static {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("alarm clock.ttf")) {
            clockFont = Font.createFont(Font.TRUETYPE_FONT, input);
        } catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }

    private final long start = System.currentTimeMillis();
    private JLabel elapsedTime;

    public StatusTimerPanel(CannedActions cannedAction, float fontSize, Color fontColor) {
        elapsedTime = new JLabel("00:00:00");
        elapsedTime.setFont(clockFont.deriveFont(fontSize));
        elapsedTime.setForeground(fontColor);

        setBorder(BorderFactory.createTitledBorder(cannedAction.name()));
        setLayout(new BorderLayout());
        add(elapsedTime, BorderLayout.CENTER);
    }
}
