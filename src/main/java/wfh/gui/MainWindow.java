package wfh.gui;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import java.awt.HeadlessException;
import java.util.List;

@Component
public class MainWindow extends JFrame {
    public MainWindow(@Value("${app.title}") String title, List<JMenu> menus) throws HeadlessException {
        super(title);

        JMenuBar menuBar = new JMenuBar();
        menus.forEach(menuBar::add);
        setJMenuBar(menuBar);

        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(640, 480);
        setLocationByPlatform(true);
    }
}
