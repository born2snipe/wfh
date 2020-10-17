package wfh.gui.status;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import wfh.settings.SettingsRepository;
import wfh.status.StatusTracker;

import static wfh.status.CannedActions.*;

@Configuration
public class StatusActionConfig {
    @Bean
    public StatusAction afkAction(SettingsRepository settingsRepository, Environment env, StatusTracker statusTracker) {
        return new StatusAction(
                AFK.name(),
                env.getProperty("status.item.AFK"),
                settingsRepository.findHotKeyFor(AFK),
                statusTracker
        );
    }

    @Bean
    public StatusAction lunchAction(SettingsRepository settingsRepository, Environment env, StatusTracker statusTracker) {
        return new StatusAction(
                LUNCH.name(),
                env.getProperty("status.item.LUNCH"),
                settingsRepository.findHotKeyFor(LUNCH),
                statusTracker
        );
    }

    @Bean
    public StatusAction workingAction(SettingsRepository settingsRepository, Environment env, StatusTracker statusTracker) {
        return new StatusAction(
                BACK_TO_WORK.name(),
                env.getProperty("status.item.BACK_TO_WORK"),
                settingsRepository.findHotKeyFor(BACK_TO_WORK),
                statusTracker
        );
    }

    @Bean
    public StatusAction doneForTheDayAction(SettingsRepository settingsRepository, Environment env, StatusTracker statusTracker) {
        return new StatusAction(
                DONE_FOR_THE_DAY.name(),
                env.getProperty("status.item.DONE_FOR_THE_DAY"),
                settingsRepository.findHotKeyFor(DONE_FOR_THE_DAY),
                statusTracker
        );
    }
}
