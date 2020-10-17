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
import java.util.List;

import static wfh.gui.swing.JMenuBuilder.newMenu;

@Configuration
public class MenuConfig {

    @Order(0)
    @Bean
    public JMenu statusActions(
            @Value("${status.menu.title}") String menuTitle,
            @Qualifier("cannedStatusActions") List<StatusAction> cannedActions,
            @Qualifier("customStatusActions") List<StatusAction> customActions,
            StatusTracker statusTracker
    ) {
        JMenuBuilder builder = newMenu(menuTitle)
                .addStatusItems(cannedActions, statusTracker);

        if (customActions.size() > 0) {
            builder.addSeparator().addStatusItems(customActions, statusTracker);
        }

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
