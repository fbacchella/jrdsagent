package jrds.agent.linux;

import java.io.BufferedReader;
import java.io.File;
import java.io.FilePermission;
import java.io.FileReader;
import java.io.IOException;
import java.security.AccessControlException;
import java.util.Map;

import jrds.agent.LProbe;

public abstract class LProbeProc extends LProbe {

    private File statFile = null; 

    @Override
    public void setProperty(String specific, String value) {
        if("statFile".equals(specific)) {
            statFile = new File(value);
        } else {
            super.setProperty(specific, value);            
        }
    }

    @Override
    public Boolean configure() {
        try {
            if(! statFile.getCanonicalPath().startsWith("/proc") && ! statFile.getCanonicalPath().startsWith("/sys") ) {
                FilePermission p = new FilePermission(statFile.getPath(), "read");
                throw new AccessControlException("access denied " + p);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(statFile != null && ! statFile.canRead()) {
            throw new RuntimeException("file '" + statFile + "' not usable");
        }
        return statFile != null &&
                statFile.canRead() &&
                super.configure();
    }

    public String getName() {
        return statFile.getAbsoluteFile().getName();
    }

    /**
     * @return the statFile
     */
    protected File getStatFile() {
        return statFile;
    }

    public Map<String, Number> query() {
        try {
            BufferedReader r = new BufferedReader(new FileReader(statFile));
            Map<String, Number> values = parse(r);
            r.close();
            return values;
        } catch (Exception e) {
            throw new RuntimeException(getName(), e);
        }
    }

    public abstract Map<String, Number> parse(BufferedReader r) throws IOException;

}
