package wfh.gui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.JFrame;
import java.awt.HeadlessException;

@Component
public class MainWindow extends JFrame {
    public MainWindow(@Value("${app.title}") String title) throws HeadlessException {
        super(title);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(640, 480);
    }
}
