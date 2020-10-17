package wfh.gui.status;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import wfh.settings.SettingsRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Configuration
public class StatusActionsConfig {
    @Bean
    public List<StatusAction> cannedStatusActions(SettingsRepository settingsRepository, Environment env) {
        ArrayList<StatusAction> actions = new ArrayList<>();
        for (CannedActions cannedAction : CannedActions.values()) {
            actions.add(new StatusAction(
                    env.getProperty("status.item." + cannedAction.name()),
                    settingsRepository.findHotKeyFor(cannedAction),
                    settingsRepository.findTaskFor(cannedAction)
            ));
        }

        return actions;
    }

    @Bean
    public List<StatusAction> customStatusActions() {
        return Collections.emptyList();
    }
}
