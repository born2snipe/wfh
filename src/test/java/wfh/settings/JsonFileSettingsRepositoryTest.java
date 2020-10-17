package wfh.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatDarculaLaf;
import com.formdev.flatlaf.FlatIntelliJLaf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static wfh.settings.Checksumer.md5;
import static wfh.status.CannedActions.*;

class JsonFileSettingsRepositoryTest {
    @Test
    public void default_colors_bad_color(@TempDir File temp) {
        writeSettingFile(temp, "{}");

        JsonFileSettingsRepository repo = new JsonFileSettingsRepository(temp);
        repo.init();

        assertThat(repo.findStatusColorFor(AFK), is(Color.YELLOW.darker()));
        assertThat(repo.findStatusColorFor(LUNCH), is(Color.GREEN.darker()));
        assertThat(repo.findStatusColorFor(WORKING), is(Color.RED));
    }

    @Test
    public void default_colors_in_settings_file(@TempDir File temp) {
        JsonFileSettingsRepository repo = new JsonFileSettingsRepository(temp);
        repo.init();

        assertThat(repo.findStatusColorFor(AFK), is(Color.YELLOW.darker()));
        assertThat(repo.findStatusColorFor(LUNCH), is(Color.GREEN.darker()));
        assertThat(repo.findStatusColorFor(WORKING), is(Color.RED));
    }

    @Test
    public void update_look_and_feel(@TempDir File temp) {
        File settings = new File(temp, ".wfh/settings.json");

        JsonFileSettingsRepository repo = new JsonFileSettingsRepository(temp);
        repo.init();

        repo.saveToUseDarkTheme(false);

        assertThat(FlatIntelliJLaf.class, is(repo.findLookAndFeel()));
        assertThat(false, is((boolean) readFileProperty(settings, "useDarkTheme")));
    }

    @Test
    public void awt_color_is_NOT_saved_to_disk(@TempDir File temp) {
        File settings = new File(temp, ".wfh/settings.json");

        JsonFileSettingsRepository repo = new JsonFileSettingsRepository(temp);
        repo.init();

        repo.saveToUseDarkTheme(false);

        Map<String, String> statusSettings = readFileProperty(settings, "working");
        assertThat(statusSettings.containsKey("awtColor"), is(false));
    }

    @Test
    public void no_look_and_feel_in_file(@TempDir File temp) {
        JsonFileSettingsRepository repo = new JsonFileSettingsRepository(temp);
        writeSettingFile(temp, "{}");
        repo.init();

        assertThat(repo.findLookAndFeel(), is(FlatDarculaLaf.class));
    }

    @Test
    public void lookAndFeelDefaulted(@TempDir File temp) {
        JsonFileSettingsRepository repo = new JsonFileSettingsRepository(temp);
        repo.init();

        assertThat(FlatDarculaLaf.class, is(repo.findLookAndFeel()));
    }

    @Test
    public void settings_already_on_disk(@TempDir File temp) {
        JsonFileSettingsRepository repo = new JsonFileSettingsRepository(temp);

        File settings = writeSettingFile(temp, "{}");

        repo.init();

        assertThat(md5(settings), is(md5("{}")));
    }

    @Test
    public void first_run_place_default_settings_file(@TempDir File temp) {
        JsonFileSettingsRepository repo = new JsonFileSettingsRepository(temp);
        repo.init();

        File settings = new File(temp, ".wfh/settings.json");
        assertThat(settings.exists(), is(true));
        assertThat(settings.isFile(), is(true));
        assertThat(md5(settings), is(md5(Thread.currentThread().getContextClassLoader().getResourceAsStream("settings.json"))));
    }

    @Test
    public void first_run_setup_directories(@TempDir File temp) {
        JsonFileSettingsRepository repo = new JsonFileSettingsRepository(temp);
        repo.init();

        File wfh = new File(temp, ".wfh");
        assertThat(wfh.exists(), is(true));
        assertThat(wfh.isDirectory(), is(true));
    }

    private <T> T readFileProperty(File settings, String propertyName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (T) mapper.readValue(settings, HashMap.class).get(propertyName);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File writeSettingFile(File temp, String json) {
        try {
            File wfh = new File(temp, ".wfh");
            wfh.mkdirs();

            File settings = new File(temp, ".wfh/settings.json");
            Files.write(settings.toPath(), json.getBytes(UTF_8));
            return settings;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}