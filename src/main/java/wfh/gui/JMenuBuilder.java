package wfh.gui;

import wfh.settings.SettingsRepository;

import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.UIManager;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class JMenuBuilder {
    private static final String LNF_BUTTON_GROUP = UUID.randomUUID().toString();
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

    public JMenuBuilder addChangeLookAndFeelItem(Class lookAndFeelClass, SettingsRepository settingsRepository) {
        JRadioButtonMenuItem item = new JRadioButtonMenuItem(lookAndFeelClass.getSimpleName());
        item.setSelected(UIManager.getLookAndFeel().getClass().equals(lookAndFeelClass));
        item.addActionListener(new ChangeLookAndFeelAction(lookAndFeelClass, settingsRepository));
        return addToButtonGroup(LNF_BUTTON_GROUP, item);
    }

    public JMenuBuilder addToButtonGroup(String buttonGroupName, JRadioButtonMenuItem item) {
        if (!nameToButtonGroup.containsKey(buttonGroupName)) {
            nameToButtonGroup.put(buttonGroupName, new ButtonGroup());
        }
        nameToButtonGroup.get(buttonGroupName).add(item);

        menu.add(item);
        return this;
    }

    public JMenuBuilder addSeparator() {
        menu.addSeparator();
        return this;
    }
}
