package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.FilePermission;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.AccessControlException;
import java.util.Map;

import jrds.agent.CollectException;
import jrds.agent.LProbe;

public abstract class LProbeProc extends LProbe {

    protected static final Path PROC_PATH = Paths.get("/proc");
    protected static final Path SYS_PATH = Paths.get("/sys");
    protected static final Path DEV_PATH = Paths.get("/dev");

    private Path statFile = null;
    private String name = null;

    @Override
    public void setProperty(String specific, String value) {
        if("statFile".equals(specific)) {
            statFile = Paths.get(value).normalize().toAbsolutePath();
        } else if ("remoteName".equals(specific)){
            name = value;
        } else {
            super.setProperty(specific, value);
        }
    }

    @Override
    public Boolean configure() {
        if (statFile == null || !Files.isReadable(statFile)) {
            throw new CollectException("File '" + statFile + "' not readable");
        }
        if (!statFile.startsWith(PROC_PATH) && !statFile.startsWith(SYS_PATH) ) {
            FilePermission p = new FilePermission(statFile.toString(), "read");
            throw new AccessControlException("access denied " + p);
        }

        if (name == null || name.isEmpty()) {
            name = statFile.toString();
        }
        return super.configure();
    }

    public String getName() {
        return name;
    }

    /**
     * @return the statFile
     */
    protected Path getStatFile() {
        return statFile;
    }

    public Map<String, Number> query() {
        try (BufferedReader r = newAsciiReader(statFile)) {
            return parse(r);
        } catch (IOException e) {
            throw new CollectException("Unable to read " + statFile + " for " + getName(), e);
        }
    }

    public abstract Map<String, Number> parse(BufferedReader r) throws IOException;

}
