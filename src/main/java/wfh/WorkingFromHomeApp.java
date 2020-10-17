package wfh;

import com.formdev.flatlaf.FlatLightLaf;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.scheduling.annotation.EnableScheduling;
import wfh.gui.MainWindow;
import wfh.gui.PreGuiLaunchHook;

import javax.swing.SwingUtilities;

@EnableScheduling
@SpringBootApplication
public class WorkingFromHomeApp {
    public static MainWindow WINDOW;

    public static void main(String[] args) {
        FlatLightLaf.install();

        ConfigurableApplicationContext context = new SpringApplicationBuilder(WorkingFromHomeApp.class)
                .headless(false)
                .run(args);

        SwingUtilities.invokeLater(() -> {
            MainWindow window = context.getBean(MainWindow.class);
            PreGuiLaunchHook hook = context.getBean(PreGuiLaunchHook.class);
            WINDOW = window;
            hook.execute(window);
            window.setVisible(true);
        });
    }
}
