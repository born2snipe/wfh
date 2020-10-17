package wfh.gui.status;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import wfh.settings.SettingsRepository;
import wfh.status.CannedActions;
import wfh.status.StatusTracker;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class StatusActionConfig {
    @Bean
    public List<StatusAction> cannedStatusActions(SettingsRepository settingsRepository, Environment env, StatusTracker statusTracker) {
        ArrayList<StatusAction> actions = new ArrayList<>();
        for (CannedActions cannedAction : CannedActions.values()) {
            actions.add(new StatusAction(
                    cannedAction.name(),
                    env.getProperty("status.item." + cannedAction.name()),
                    settingsRepository.findHotKeyFor(cannedAction),
                    statusTracker
            ));
        }

        return actions;
    }
}
