package wfh.gui.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wfh.status.Status;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;

public class StatusTimerPanel extends JPanel {
    private static final Logger LOGGER = LoggerFactory.getLogger(StatusTimerPanel.class);
    private static Font clockFont;

    static {
        try (InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("alarm clock.ttf")) {
            clockFont = Font.createFont(Font.TRUETYPE_FONT, input);
        } catch (IOException | FontFormatException e) {
            LOGGER.error("Failed to read custom font from the JAR", e);
        }
    }

    private JLabel elapsedTime;

    public StatusTimerPanel(Status cannedAction, float fontSize, Color fontColor) {
        elapsedTime = new JLabel("00:00:00");
        elapsedTime.setFont(clockFont.deriveFont(fontSize));
        elapsedTime.setForeground(fontColor);

        setBorder(BorderFactory.createTitledBorder(cannedAction.name()));
        setLayout(new BorderLayout());
        add(elapsedTime, BorderLayout.CENTER);
    }

    public void setElapsedTime(long elapsedTime) {
        SwingUtilities.invokeLater(() -> this.elapsedTime.setText(ElapsedTimeFormatter.format(elapsedTime)));
    }
}
