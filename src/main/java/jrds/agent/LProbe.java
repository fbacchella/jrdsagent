package jrds.agent;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.concurrent.TimeoutException;

public abstract class LProbe {

    public Boolean configure() {
        return true;
    }

    public abstract Map<String, Number> query();

    /**
     * Used to generate an uniq name for the local instance of the probe
     * can be meaningless (not intend to human use), but must be
     * persistent accross reboot
     * @return the probe instance name
     */
    public abstract String getName();

    /**
     * Some property that might be configured for this prope
     * Each implementation should overide it and catch intersting property
     * but don't forget to call super(...) if not intersted in it
     * @param specific
     * @param value
     */
    public void setProperty(String specific, String value) {
    }

    protected BufferedReader newAsciiReader(String file) throws IOException {
        return newAsciiReader(Paths.get(file));
    }

    protected BufferedReader newAsciiReader(File file) throws IOException {
        return newAsciiReader(file.toPath());
    }

    protected BufferedReader newAsciiReader(Path file) throws IOException {
        byte[] byteBuffer = Files.readAllBytes(file);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteBuffer);
        InputStreamReader inputStreamReader = new InputStreamReader(byteArrayInputStream, StandardCharsets.US_ASCII);
        return new BufferedReader(inputStreamReader);
    }

    protected Iterable<CharSequence> readLines(Path path) throws IOException {
        return readLines(path, 0);
    }

    protected Iterable<CharSequence> readLines(Path path, int skip) throws IOException {
        try {
            AsciiCharSequence content = AsciiCharSequence.of(path, null);
            return content.readLines(skip);
        } catch (TimeoutException e) {
            // Unreachable since timeout value is null
            return null;
        }
    }

    protected CharSequence readLine(Path path) throws IOException {
        try (BufferedReader reader = newAsciiReader(path)){
            return reader.readLine();
        }
    }

}
