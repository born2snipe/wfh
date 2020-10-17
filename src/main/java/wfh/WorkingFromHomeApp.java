package wfh;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import wfh.gui.MainWindow;

import javax.swing.SwingUtilities;

@SpringBootApplication
public class WorkingFromHomeApp {
    public static void main(String[] args) {
        ConfigurableApplicationContext context = new SpringApplicationBuilder(WorkingFromHomeApp.class)
                .headless(false)
                .run(args);

        SwingUtilities.invokeLater(() -> {
            MainWindow window = context.getBean(MainWindow.class);
            window.setVisible(true);
        });
    }
}
