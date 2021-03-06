package wfh.gui;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import wfh.gui.status.StatusAction;
import wfh.gui.swing.JMenuBuilder;
import wfh.settings.SettingsRepository;
import wfh.status.StatusTracker;

import javax.swing.JMenu;

import static wfh.gui.swing.JMenuBuilder.newMenu;

@Configuration
public class MenuConfig {

    @Order(0)
    @Bean
    public JMenu statusActions(
            @Value("${status.menu.title}") String menuTitle,
            @Qualifier("afkAction") StatusAction afkAction,
            @Qualifier("lunchAction") StatusAction lunchAction,
            @Qualifier("workingAction") StatusAction workingAction,
            @Qualifier("doneForTheDayAction") StatusAction doneForTheDayAction,
            StatusTracker statusTracker
    ) {
        JMenuBuilder builder = newMenu(menuTitle)
                .addStatusItem(workingAction, statusTracker)
                .addSeparator()
                .addStatusItem(afkAction, statusTracker)
                .addStatusItem(lunchAction, statusTracker)
                .addSeparator()
                .addStatusItem(doneForTheDayAction, statusTracker);

        return builder.build();
    }

    @Order(Integer.MAX_VALUE)
    @Bean
    public JMenu lookAndFeelMenu(
            @Value("${theme.menu.title}") String menuTitle,
            @Value("${theme.menu.dark.title}") String darkTitle,
            @Value("${theme.menu.light.title}") String lightTitle,
            SettingsRepository settingsRepository) {
        return newMenu(menuTitle)
                .addChangeThemeItem(true, darkTitle, settingsRepository)
                .addChangeThemeItem(false, lightTitle, settingsRepository)
                .build();
    }
}
