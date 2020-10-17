package wfh.gui.swing;

import wfh.gui.status.StatusAction;
import wfh.gui.theme.ChangeThemeAction;
import wfh.settings.SettingsRepository;
import wfh.status.StatusTracker;

import javax.swing.Action;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class JMenuBuilder {
    private static final String THEME_BUTTON_GROUP = UUID.randomUUID().toString();
    private static final String STATUS_BUTTON_GROUP = UUID.randomUUID().toString();
    private final JMenu menu;
    private final Map<String, ButtonGroup> nameToButtonGroup = new HashMap<>();

    private JMenuBuilder(String name) {
        menu = new JMenu(name);
        menu.setMnemonic(name.charAt(0));
    }

    public static JMenuBuilder newMenu(String name) {
        return new JMenuBuilder(name);
    }

    public JMenu build() {
        return menu;
    }

    public JMenuBuilder addChangeThemeItem(boolean useDarkTheme, String text, SettingsRepository settingsRepository) {
        JRadioButtonMenuItem item = new JRadioButtonMenuItem(text);
        item.setSelected(useDarkTheme == settingsRepository.useDarkTheme());
        item.addActionListener(new ChangeThemeAction(useDarkTheme, settingsRepository));
        return addToButtonGroup(THEME_BUTTON_GROUP, item);
    }

    public JMenuBuilder addToButtonGroup(String buttonGroupName, JRadioButtonMenuItem item) {
        if (!nameToButtonGroup.containsKey(buttonGroupName)) {
            nameToButtonGroup.put(buttonGroupName, new ButtonGroup());
        }
        nameToButtonGroup.get(buttonGroupName).add(item);
        return addItem(item);
    }

    public JMenuBuilder addItem(Action action) {
        return addItem(new JMenuItem(action));
    }

    public JMenuBuilder addItem(JMenuItem item) {
        menu.add(item);
        return this;
    }

    public JMenuBuilder addSeparator() {
        menu.addSeparator();
        return this;
    }

    public JMenuBuilder addStatusItems(List<StatusAction> actions, StatusTracker statusTracker) {
        actions.forEach((action) -> {
            JRadioButtonMenuItem item = new JRadioButtonMenuItem(action);
            item.setSelected(statusTracker.getCurrentStatus().equals(action.getName()));
            addToButtonGroup(STATUS_BUTTON_GROUP, item);
        });
        return this;
    }
}
