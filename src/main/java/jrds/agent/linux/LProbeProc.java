package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilePermission;
import java.io.IOException;
import java.security.AccessControlException;
import java.util.Map;

import jrds.agent.LProbe;

public abstract class LProbeProc extends LProbe {

    private File statFile = null;
    private String name = null;

    @Override
    public void setProperty(String specific, String value) {
        if("statFile".equals(specific)) {
            statFile = new File(value);
        } else if ("remoteName".equals(specific)){
            name = value;
        } else {
            super.setProperty(specific, value);
        }
    }

    @Override
    public Boolean configure() {
        if (statFile == null || ! statFile.canRead()) {
            throw new RuntimeException("file '" + statFile + "' not usable");
        }
        try {
            if (!statFile.getCanonicalPath().startsWith("/proc") && !statFile.getCanonicalPath().startsWith("/sys") ) {
                FilePermission p = new FilePermission(statFile.getPath(), "read");
                throw new AccessControlException("access denied " + p);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if (name == null || name.isEmpty()) {
            name = statFile.getAbsoluteFile().getName();
        }
        return super.configure();
    }

    public String getName() {
        return name;
    }

    /**
     * @return the statFile
     */
    protected File getStatFile() {
        return statFile;
    }

    public Map<String, Number> query() {
        try (BufferedReader r = newAsciiReader(statFile)) {
            Map<String, Number> values = parse(r);
            return values;
        } catch (IOException e) {
            throw new RuntimeException("unable to read " + statFile + " for " + getName(), e);
        }
    }

    public abstract Map<String, Number> parse(BufferedReader r) throws IOException;

}
