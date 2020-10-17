package wfh.settings;

import org.springframework.util.DigestUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import static java.nio.charset.StandardCharsets.UTF_8;

public class Checksumer {
    public static String md5(File file) {
        try {
            return md5(new FileInputStream(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public static String md5(String data) {
        return md5(new ByteArrayInputStream(data.getBytes(UTF_8)));
    }

    public static String md5(InputStream inputStream) {
        try (InputStream input = inputStream) {
            return DigestUtils.md5DigestAsHex(input);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
