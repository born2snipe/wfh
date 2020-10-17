package wfh.settings;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.formdev.flatlaf.FlatDarculaLaf;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static wfh.settings.Checksumer.md5;

class JsonFileSettingsRepositoryTest {
    @Test
    public void update_look_and_feel(@TempDir File temp) {
        File settings = new File(temp, ".wfh/settings.json");

        JsonFileSettingsRepository repo = new JsonFileSettingsRepository(temp);
        repo.init();

        repo.saveLookAndFeel(NimbusLookAndFeel.class);

        assertThat(NimbusLookAndFeel.class, is(repo.findLookAndFeel()));
        assertThat(NimbusLookAndFeel.class.getName(), is(readFileProperty(settings, "lookAndFeelClassName")));
    }

    @Test
    public void bad_look_and_feel_in_file(@TempDir File temp) {
        writeSettingFile(temp, "{\"lookAndFeelClassName\": \"class.does.not.exist\"}");

        JsonFileSettingsRepository repo = new JsonFileSettingsRepository(temp);
        repo.init();

        assertThat(FlatDarculaLaf.class, is(repo.findLookAndFeel()));
    }

    @Test
    public void no_look_and_feel_in_file(@TempDir File temp) {
        JsonFileSettingsRepository repo = new JsonFileSettingsRepository(temp);
        writeSettingFile(temp, "{}");
        repo.init();

        assertThat(FlatDarculaLaf.class, is(repo.findLookAndFeel()));
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

    private String readFileProperty(File settings, String propertyName) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return (String) mapper.readValue(settings, HashMap.class).get(propertyName);
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