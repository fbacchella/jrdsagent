package jrds.agent;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

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
     * but dont forger to call super(...) if not intersted in it
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
        return Files.newBufferedReader(file, StandardCharsets.US_ASCII);
    }

}
