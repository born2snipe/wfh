package wfh.gui;

import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import com.formdev.flatlaf.FlatLightLaf;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import wfh.settings.SettingsRepository;

import javax.swing.JMenu;

import static wfh.gui.swing.JMenuBuilder.newMenu;

@Configuration
public class DefaultMenuConfig {

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
