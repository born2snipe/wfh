package wfh.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import wfh.gui.status.StatusAction;
import wfh.gui.swing.JMenuBuilder;
import wfh.settings.SettingsRepository;

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
            @Qualifier("customStatusActions") List<StatusAction> customActions
    ) {
        JMenuBuilder builder = newMenu(menuTitle)
                .addStatusItems(cannedActions);

        if (customActions.size() > 0) {
            builder.addSeparator().addStatusItems(customActions);
        }

        return builder.build();
    }

    @Order(Integer.MAX_VALUE)
    @Bean
    public JMenu lookAndFeelMenu(@Value("${theme.menu.title}") String menuTitle, SettingsRepository settingsRepository) {
        return newMenu(menuTitle)
                .addChangeLookAndFeelItem(FlatLightLaf.class, settingsRepository)
                .addChangeLookAndFeelItem(FlatDarkLaf.class, settingsRepository)
                .addChangeLookAndFeelItem(FlatIntelliJLaf.class, settingsRepository)
                .addChangeLookAndFeelItem(FlatDarculaLaf.class, settingsRepository)
                .build();
    }
}
