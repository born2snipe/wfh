package wfh.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatDarculaLaf;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import wfh.status.CannedActions;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Optional;

@Repository
public class JsonFileSettingsRepository implements SettingsRepository {
    private final Logger logger = LoggerFactory.getLogger(getClass());
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final File homeDirectory;
    private File settingsFile;
    private Settings currentSettings;

    public JsonFileSettingsRepository(@Value("${user.home}") File homeDirectory) {
        this.homeDirectory = homeDirectory;
    }

    @Override
    public void saveLookAndFeel(Class lookAndFeelClass) {
        currentSettings.setLookAndFeelClassName(lookAndFeelClass.getName());
        saveChangesToDisk();
    }

    @Override
    public Class findLookAndFeel() {
        return Optional.ofNullable(currentSettings.getLookAndFeelClassName())
                .map(this::loadClass)
                .orElse(FlatDarculaLaf.class);
    }

    @Override
    public Optional<String> findHotKeyFor(CannedActions cannedAction) {
        return Optional.empty();
    }

    @PostConstruct
    public void init() {
        File wfhDir = new File(homeDirectory, ".wfh");
        wfhDir.mkdirs();

        settingsFile = new File(wfhDir, "settings.json");
        if (!settingsFile.exists()) {
            copySettingToDisk();
        }

        try {
            currentSettings = objectMapper.readValue(settingsFile, Settings.class);
        } catch (IOException e) {
            logger.error("Failed to read settings file: " + settingsFile, e);
        }
    }

    private void saveChangesToDisk() {
        try (OutputStream output = new FileOutputStream(settingsFile)) {
            objectMapper.writeValue(output, currentSettings);
        } catch (IOException e) {
            logger.error("Failed to write settings to disk", e);
        }
    }

    private void copySettingToDisk() {
        try (
                InputStream input = Thread.currentThread().getContextClassLoader().getResourceAsStream("settings.json");
                OutputStream output = new FileOutputStream(settingsFile);
        ) {
            IOUtils.copy(input, output);
            logger.info("Wrote default settings to disk: " + settingsFile);
        } catch (IOException e) {
            logger.error("Failed to write default settings to disk", e);
        }
    }

    private Class loadClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
